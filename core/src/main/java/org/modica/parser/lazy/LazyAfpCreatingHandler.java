package org.modica.parser.lazy;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.parser.StructuredFieldHandler;
import org.modica.parser.StructuredFieldIntroducerHandler;
import org.modica.parser.lazy.LazyAfp.FileChannelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is used to trigger the creation of parsing contexts
 * TODO Currently we throw away all the structured fields in the anticipation of memory overhead
 * A strategy could be in place to preserve some, setting them on the factory
 */
public class LazyAfpCreatingHandler implements StructuredFieldIntroducerHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LazyAfpCreatingHandler.class);

    //  TODO inject - must uses current or single thread only!!!
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final List<StructuredField> lazyStructuredFields;

    private final LazyStructuredFieldFactory factory;

    private final StructuredFieldHandler creationHandler;

    private final CountDownLatch streamShutdown;

    private final FileChannelProvider fileChannelProvider;

    public LazyAfpCreatingHandler(StructuredFieldHandler sfHandler, FileInputStream input) {
        this.creationHandler = sfHandler;
        this.streamShutdown = new CountDownLatch(1);
        this.fileChannelProvider = new FileChannelProvider(null);
        factory = new LazyStructuredFieldFactory(input.getChannel());
        lazyStructuredFields = new ArrayList<StructuredField>();
    }

    private void await() throws InterruptedException {
        LOG.debug("Awaiting completion of lazy parse...");
        streamShutdown.await();
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
                LOG.debug("Lazy parse completed");
            }
        });
        executor.shutdown();
    }

    private LazyStructuredField createLazyStructuredField(StructuredFieldIntroducer introducer,
            Callable<Context> contextResolver) {
        final LazyStructuredField lazyStructuredField = new LazyStructuredField(introducer,
                executor.submit(contextResolver), fileChannelProvider);
        lazyStructuredFields.add(lazyStructuredField);
        return lazyStructuredField;
    }

    @Override
    public void handleBegin(final StructuredFieldIntroducer introducer) {
        LazyStructuredField structuredField = createLazyStructuredField(introducer,
                new Callable<Context>() {
            @Override
            public Context call() throws Exception {
                factory.createBegin(introducer);
                return factory.getPreviousContext();
            }
        });
        creationHandler.handleBegin(structuredField);
    }

    @Override
    public void handleEnd(final StructuredFieldIntroducer introducer) {
        LazyStructuredField structuredField = createLazyStructuredField(introducer,
                new Callable<Context>() {
            @Override
            public Context call() throws Exception {
                factory.createEnd(introducer);
                return factory.getPreviousContext();
            }
        });
        creationHandler.handleEnd(structuredField);
    }

    @Override
    public void handle(final StructuredFieldIntroducer introducer) {
        Callable<Context> callable = new Callable<Context>() {
            @Override
            public Context call() throws Exception {
                switch (introducer.getType().getTypeCode()) {
                case Attribute:
                    factory.createMap(introducer);
                    break;
                case CopyCount:
                    factory.createCopyCount(introducer);
                    break;
                case Map:
                    factory.createMap(introducer);
                    break;
                case Descriptor:
                    factory.createDescriptor(introducer);
                    break;
                case Migration:
                    factory.createMigration(introducer);
                    break;
                case Data:
                    factory.createData(introducer);
                    break;
                case Position:
                    factory.createPosition(introducer);
                    break;
                case Include:
                    factory.createInclude(introducer);
                    break;
                case Control:
                    factory.createControl(introducer);
                    break;
                case Index:
                    factory.createIndex(introducer);
                    break;
                case Link:
                    factory.createLink(introducer);
                    break;
                case Orientation:
                    factory.createOrientation(introducer);
                    break;
                case Process:
                    factory.createProcess(introducer);
                    break;
                case Table:
                    factory.createTable(introducer);
                    break;
                case Variable:
                    factory.createVariable(introducer);
                    break;
                case Begin:
                case End:
                case Unknown:
                    throw new IllegalArgumentException("Should not get here.");
                }
                return factory.getPreviousContext();
            }
        };
        creationHandler.handle(createLazyStructuredField(introducer, callable));
    }

    public static class LazyParseLatch {
        private LazyAfpCreatingHandler creator;
        LazyParseLatch(LazyAfpCreatingHandler creator) {
            this.creator = creator;
        }

        public void await() {
            if (creator != null) {
                try {
                    creator.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                creator = null;
            }
        }
    }

    public LazyAfp getLazyAfp() {
        return new LazyAfp(lazyStructuredFields, fileChannelProvider, new LazyParseLatch(this));
    }
}
