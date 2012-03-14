package org.modica.parser.lazy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.StructuredFieldFactory;
import org.modica.afp.modca.StructuredFieldFactoryImpl;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.parser.PrintingSFHandler;
import org.modica.parser.PrintingSFIntroducerHandler;
import org.modica.parser.StructuredFieldCreator;
import org.modica.parser.StructuredFieldIntroducerHandler;
import org.modica.parser.StructuredFieldIntroducerHandlers;
import org.modica.parser.StructuredFieldIntroducerParser;
import org.modica.parser.lazy.LazyStructuredFieldFactory.CreationListener;

public class LazyParser {

    public static void main(String[] args) throws IOException, InterruptedException {
        final FileInputStream input = new FileInputStream(new File(args[0]));
        final LinkedHashMap<StructuredFieldIntroducer, StructuredFieldProxy> sfs
        = new LinkedHashMap<StructuredFieldIntroducer, StructuredFieldProxy>();
        ProxyCreatingHandler proxyCreator = new ProxyCreatingHandler(sfs);
        StructuredFieldIntroducerHandler handlers = StructuredFieldIntroducerHandlers.chain(proxyCreator,
                new PrintingSFIntroducerHandler(System.out));
        StructuredFieldIntroducerParser preParser = new StructuredFieldIntroducerParser(input, handlers);

        // Create a lazy SFTree
        preParser.parse();

        final CountDownLatch doneSignal = new CountDownLatch(1);
        new Thread( new Runnable() {
            @Override
            public void run() {
                // Whilst application is idle, lets pre-load the real structured fields
                StructuredFieldFactory factory = new LazyStructuredFieldFactory(input.getChannel(),
                        new ContextRecorder(sfs));
                StructuredFieldIntroducerHandler structuredFieldCreator = new StructuredFieldCreator(
                        factory, new PrintingSFHandler(System.out));
                StructuredFieldIntroducerHandler handlers = StructuredFieldIntroducerHandlers.chain(structuredFieldCreator,
                        new PrintingSFIntroducerHandler(System.out));
                StructuredFieldIntroducerParser prime = new StructuredFieldIntroducerParser(sfs.keySet(), handlers);
                // This parse will create the contexts, but not retain the structured fields
                // TODO do this in a different thread immediately after pre-parse stage creating futures representing the real structured field parsing contexts
                try {
                    prime.parse();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                doneSignal.countDown();
            }
        }).start();

        // return sf model
        for (StructuredFieldProxy sf : sfs.values()) {
            System.out.println(sf);
        }

        // simulate new session 
        final FileInputStream newInput = new FileInputStream(new File(args[0]));
        // We need to control access to Full SFs in a better way!
        doneSignal.await();
        for (StructuredFieldProxy sf : sfs.values()) {
            System.out.println(sf);
            System.out.println(sf.context);
            sf.attach(newInput.getChannel());
        }
        // simulate lazy load
        for (StructuredFieldProxy sf : sfs.values()) {
            System.out.println(sf.getParameters());
        }
        // simulate session end 
        for (StructuredFieldProxy sf : sfs.values()) {
            sf.detach();
        }
        newInput.close();
    }


    // TODO synchronize access to the proxy structured field
    public static class ContextRecorder implements CreationListener {

        private final LinkedHashMap<StructuredFieldIntroducer, StructuredFieldProxy> sfs;

        ContextRecorder(LinkedHashMap<StructuredFieldIntroducer, StructuredFieldProxy> sfs) {
            this.sfs = sfs;
        }

        @Override
        public void created(StructuredFieldIntroducer introducer, Context context) {
            sfs.get(introducer).setContext(context);

        }

    }

    public static class ProxyCreatingHandler implements StructuredFieldIntroducerHandler {

        private final LinkedHashMap<StructuredFieldIntroducer, StructuredFieldProxy> sfs;

        ProxyCreatingHandler(LinkedHashMap<StructuredFieldIntroducer, StructuredFieldProxy> sfs) {
            this.sfs = sfs;
        }

        @Override
        public void startAfp() {
        }

        @Override
        public void endAfp() {
        }

        private void add(StructuredFieldIntroducer introducer) {
            sfs.put(introducer, new StructuredFieldProxy(introducer));
        }

        @Override
        public void handleBegin(StructuredFieldIntroducer sf) {
            add(sf);
        }

        @Override
        public void handleEnd(StructuredFieldIntroducer sf) {
            add(sf);
        }

        @Override
        public void handle(StructuredFieldIntroducer sf) {
            add(sf);
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

        private Context context;

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
        }

        @Override
        public List<ParameterAsString> getParameters() {
            if (structuredField == null) {
                load();
            }
            return structuredField.getParameters();
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public String toString() {
            return structuredField == null ? "Proxy: " + introducer.toString() : structuredField.toString();
        }
    }

}
