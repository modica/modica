package org.afpparser.parser;

import org.afpparser.afp.modca.AbstractStructuredField;
import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.StructuredField;
import org.afpparser.afp.modca.StructuredFieldFactory;

/**
 * The top level class for handling structured field creation given the
 * FileChannel of an AFP document. TODO: Add a more useful javadoc here when we
 * know what it's supposed to be doing
 */
public class StructuredFieldCreator implements SFIntroducerHandler {

    private final StructuredFieldHandler creationHandler;

    private final StructuredFieldFactory sfFactory;

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
