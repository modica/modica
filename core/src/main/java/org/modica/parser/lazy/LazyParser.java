package org.modica.parser.lazy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.StructuredFieldFactory;
import org.modica.afp.modca.StructuredFieldFactoryImpl;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.parser.PrintingSFHandler;
import org.modica.parser.PrintingSFIntroducerHandler;
import org.modica.parser.StructuredFieldHandler;
import org.modica.parser.StructuredFieldIntroducerHandler;
import org.modica.parser.StructuredFieldIntroducerHandlers;
import org.modica.parser.StructuredFieldIntroducerParser;

public class LazyParser {

    public static void main(String[] args) throws IOException {
        final FileInputStream input = new FileInputStream(new File(args[0]));
        final CountDownLatch streamShutdown = new CountDownLatch(1);
        ProxyCreatingHandler proxyCreator = new ProxyCreatingHandler(
                new PrintingSFHandler(System.out), input, streamShutdown);
        StructuredFieldIntroducerHandler handlers = StructuredFieldIntroducerHandlers.chain(proxyCreator,
                new PrintingSFIntroducerHandler(System.out));
        StructuredFieldIntroducerParser preParser = new StructuredFieldIntroducerParser(input, handlers);
        // Create a lazy SFTree
        preParser.parse();
        // Do not close the stream until all the contexts have been resolved
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    streamShutdown.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        List<StructuredFieldProxy> fields = proxyCreator.getFields();
        // Can return the model now
        for (StructuredFieldProxy sf : fields) {
            System.out.println(sf);
            sf.detach();
        }
        // simulate new session 
        final FileInputStream newInput = new FileInputStream(new File(args[0]));
        // We need to control access to Full SFs in a better way!
        for (StructuredFieldProxy sf : fields) {
            sf.attach(newInput.getChannel());
        }
        // simulate trigger of lazy load
        for (StructuredField sf : fields) {
            System.out.println(sf.getParameters());
        }
        // simulate session end 
        for (StructuredFieldProxy sf : fields) {
            sf.detach();
        }
        newInput.close();
    }

    /**
     * This is used to trigger the creation of parsing contexts
     * TODO Currently we throw away all the structured fields in the anticipation of memory overhead
     * A strategy could be in place to preserve some, setting them on the factory
     */
    public static class ProxyCreatingHandler implements StructuredFieldIntroducerHandler {

        //  TODO inject - must uses current or single thread only!!!
        private final ExecutorService executor = Executors.newSingleThreadExecutor();
        
        private final List<StructuredFieldProxy> fields;

        private final LazyStructuredFieldFactory factory;

        private final StructuredFieldHandler creationHandler;

        private final CountDownLatch streamShutdown;
        
        private final FileChannel fileChannel;

        public ProxyCreatingHandler(StructuredFieldHandler sfHandler, FileInputStream input, CountDownLatch streamShutdown) {
            this.creationHandler = sfHandler;
            this.fileChannel = input.getChannel();
            factory = new LazyStructuredFieldFactory(fileChannel);
            this.streamShutdown = streamShutdown;
            fields = new ArrayList<StructuredFieldProxy>();
        }

        @Override
        public void startAfp() {
            creationHandler.startAfp();
        }

        @Override
        public void endAfp() {
            creationHandler.endAfp();
            executor.submit(new Runnable() {
                public void run() {
                    streamShutdown.countDown();
                }
            });
        }

        private StructuredFieldProxy wrap(StructuredFieldIntroducer introducer, Callable<Context> contextResolver) {
            final StructuredFieldProxy proxy = new StructuredFieldProxy(introducer);
            proxy.attach(fileChannel);
            proxy.contextFuture = executor.submit(contextResolver);
            fields.add(proxy);
            return proxy;
        }

        @Override
        public void handleBegin(final StructuredFieldIntroducer introducer) {
            StructuredFieldProxy proxy = wrap(introducer,
                    new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createBegin(introducer);
                    return factory.getLast();
                }
            });
            creationHandler.handleBegin(proxy);
        }

        @Override
        public void handleEnd(final StructuredFieldIntroducer introducer) {
            StructuredFieldProxy proxy = wrap(introducer,
                    new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createEnd(introducer);
                    return factory.getLast();
                }
            });
            creationHandler.handleEnd(proxy);
        }

        @Override
        public void handle(final StructuredFieldIntroducer introducer) {

            Callable<Context> callable;

            switch (introducer.getType().getTypeCode()) {
            case Map:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        factory.createMap(introducer);
                        return factory.getLast();
                    }
                };
                break;
            case Descriptor:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        factory.createDescriptor(introducer);
                        return factory.getLast();
                    }
                };
                break;
            case Migration:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        factory.createMigration(introducer);
                        return factory.getLast();
                    }
                };
                break;
            case Data:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        factory.createData(introducer);
                        return factory.getLast();
                    }
                };
                break;
            case Position:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        factory.createPosition(introducer);
                        return factory.getLast();
                    }
                };
                break;
            case Include:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        factory.createInclude(introducer);
                        return factory.getLast();
                    }
                };
                break;
            case Control:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        factory.createControl(introducer);
                        return factory.getLast();
                    }
                };
                break;
            case Index:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        factory.createIndex(introducer);
                        return factory.getLast();
                    }
                };
                break;
            default:
                callable = new Callable<Context>() {
                    @Override
                    public Context call() throws Exception {
                        return null;
                    }
                };
            }
            creationHandler.handle(wrap(introducer, callable));
        }
        public List<StructuredFieldProxy> getFields() {
            return Collections.unmodifiableList(fields);
        }
    }

    public static class StructuredFieldProxy extends AbstractStructuredField {

        private static final StructuredField SF_GUARD = new AbstractStructuredField(null) {
            @Override
            public List<ParameterAsString> getParameters() {
                return Collections.emptyList();
            }
        };

        private StructuredField structuredField;

        private Future<Context> contextFuture;

        private final StructuredFieldIntroducer introducer;

        private transient FileChannel fileChannel;

        public StructuredFieldProxy(StructuredFieldIntroducer introducer) {
            super(introducer);
            this.introducer = introducer;
        }

        public void attach(FileChannel fileChannel) {
            this.fileChannel = fileChannel;
        }

        public void detach() {
            fileChannel = null;
        }

        private void load() {
            Context context;
            try {
                context = contextFuture.get();
                contextFuture = null;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            if (context == null) {
                structuredField = SF_GUARD;
                return;
            }
            StructuredFieldFactory factory = new StructuredFieldFactoryImpl(fileChannel, context);
            switch (getType().getTypeCode()) {
            case Begin:
                structuredField = factory.createBegin(introducer);
                break;
            case End:
                structuredField = factory.createEnd(introducer);
                break;
            case Map:
                structuredField = factory.createMap(introducer);
                break;
            case Descriptor:
                structuredField = factory.createDescriptor(introducer);
                break;
            case Migration:
                structuredField = factory.createMigration(introducer);
                break;
            case Data:
                structuredField = factory.createData(introducer);
                break;
            case Position:
                structuredField = factory.createPosition(introducer);
                break;
            case Include:
                structuredField = factory.createInclude(introducer);
                break;
            case Control:
                structuredField = factory.createControl(introducer);
                break;
            case Index:
                structuredField = factory.createIndex(introducer);
                break;
            default:
                structuredField = SF_GUARD;
            }
            if (structuredField == null) {
                structuredField = SF_GUARD;
            }
        }

        @Override
        public List<ParameterAsString> getParameters() {
            if (structuredField == null) {
                load();
            }
            return structuredField.getParameters();
        }

        @Override
        public String toString() {
            return structuredField == null ? "Proxy: " + introducer.toString() : structuredField.toString();
        }
    }

}
