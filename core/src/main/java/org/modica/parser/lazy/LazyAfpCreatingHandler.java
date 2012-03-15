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

/**
 * This is used to trigger the creation of parsing contexts
 * TODO Currently we throw away all the structured fields in the anticipation of memory overhead
 * A strategy could be in place to preserve some, setting them on the factory
 */
public class LazyAfpCreatingHandler implements StructuredFieldIntroducerHandler {

    //  TODO inject - must uses current or single thread only!!!
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final List<StructuredField> lazyStructuredFields;

    private final LazyStructuredFieldFactory factory;

    private final StructuredFieldHandler creationHandler;

    private final CountDownLatch streamShutdown;

    private final FileChannelProvider fileChannelProvider;

    public LazyAfpCreatingHandler(StructuredFieldHandler sfHandler, FileInputStream input,
            CountDownLatch streamShutdown) {
        this.creationHandler = sfHandler;
        this.fileChannelProvider = new FileChannelProvider(input.getChannel());
        factory = new LazyStructuredFieldFactory(input.getChannel());
        this.streamShutdown = streamShutdown;
        lazyStructuredFields = new ArrayList<StructuredField>();
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
        Callable<Context> callable;
        switch (introducer.getType().getTypeCode()) {
        case Map:
            callable = new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createMap(introducer);
                    return factory.getPreviousContext();
                }
            };
            break;
        case Descriptor:
            callable = new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createDescriptor(introducer);
                    return factory.getPreviousContext();
                }
            };
            break;
        case Migration:
            callable = new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createMigration(introducer);
                    return factory.getPreviousContext();
                }
            };
            break;
        case Data:
            callable = new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createData(introducer);
                    return factory.getPreviousContext();
                }
            };
            break;
        case Position:
            callable = new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createPosition(introducer);
                    return factory.getPreviousContext();
                }
            };
            break;
        case Include:
            callable = new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createInclude(introducer);
                    return factory.getPreviousContext();
                }
            };
            break;
        case Control:
            callable = new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createControl(introducer);
                    return factory.getPreviousContext();
                }
            };
            break;
        case Index:
            callable = new Callable<Context>() {
                @Override
                public Context call() throws Exception {
                    factory.createIndex(introducer);
                    return factory.getPreviousContext();
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
        creationHandler.handle(createLazyStructuredField(introducer, callable));
    }

    public LazyAfp getLazyAfp() {
        return new LazyAfp(lazyStructuredFields, fileChannelProvider);
    }
}