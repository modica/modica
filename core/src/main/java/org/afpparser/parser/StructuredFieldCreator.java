package org.afpparser.parser;

import org.afpparser.afp.modca.StructuredFieldFactory;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 * This class captures SFIntroduce creating events and delegates the creating of
 * StructuredFields to {@link StructuredFieldFactory} and publishes creation
 * events to a {@link StructuredFieldHandler} for further processing.
 */
public class StructuredFieldCreator implements SFIntroducerHandler {

    private final StructuredFieldHandler creationHandler;

    private final StructuredFieldFactory sfFactory;

    /**
     * Create a new instance.
     *
     * @param structuredFieldFactory
     *            The factory for creating {@link StructuredField}s.
     * @param structuredFieldHandler
     *            The handler to publish creation events to.
     */
    public StructuredFieldCreator(StructuredFieldFactory structuredFieldFactory,
            StructuredFieldHandler structuredFieldHandler) {
        this.sfFactory = structuredFieldFactory;
        this.creationHandler = structuredFieldHandler;
    }

    @Override
    public void startAfp() {
    }

    @Override
    public void endAfp() {
    }

    @Override
    public void handleBegin(SfIntroducer introducer) {
        StructuredField structuredField = sfFactory.createBegin(introducer);
        handle(introducer, structuredField);
    }

    @Override
    public void handleEnd(SfIntroducer sf) {
    }

    @Override
    public void handle(SfIntroducer introducer) {
        StructuredField structuredField;
        switch (introducer.getType().getTypeCode()) {
        case Map:
            structuredField = sfFactory.createMap(introducer);
            break;
        case Descriptor:
            structuredField = sfFactory.createDescriptor(introducer);
            break;
        case Migration:
            structuredField = sfFactory.createMigration(introducer);
            break;
        default:
            structuredField = null;
        }
        handle(introducer, structuredField);
    }

    private void handle(SfIntroducer introducer, StructuredField structuredField) {
        // TODO remove UnhandledStructuredField when all structured fields can
        // be created
        creationHandler.handle(structuredField != null ? structuredField
                : new UnhandledStructuredField(introducer));
    }

    private static class UnhandledStructuredField extends AbstractStructuredField {
        private final SfIntroducer introducer;

        public UnhandledStructuredField(SfIntroducer introducer) {
            super(introducer);
            this.introducer = introducer;
        }

        @Override
        public String toString() {
            return "UNHANDLED SF: introducer " + introducer;
        }
    }
}
