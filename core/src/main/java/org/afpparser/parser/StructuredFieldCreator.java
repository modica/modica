package org.afpparser.parser;

import java.util.Collections;
import java.util.Map;

import org.afpparser.afp.modca.StructuredFieldFactory;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 * This class captures SFIntroduce creating events and delegates the creating of StructuredFields to
 * {@link StructuredFieldFactory} and publishes creation events to a {@link StructuredFieldHandler}
 * for further processing.
 */
public class StructuredFieldCreator implements StructuredFieldIntroducerHandler {

    private final StructuredFieldHandler creationHandler;

    private final StructuredFieldFactory sfFactory;

    /**
     * Create a new instance.
     *
     * @param structuredFieldFactory The factory for creating {@link StructuredField}s.
     * @param structuredFieldHandler The handler to publish creation events to.
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
    public void handleBegin(StructuredFieldIntroducer introducer) {
        StructuredField structuredField = sfFactory.createBegin(introducer);
        handle(introducer, structuredField);
    }

    @Override
    public void handleEnd(StructuredFieldIntroducer introducer) {
        StructuredField structuredField = sfFactory.createEnd(introducer);
        handle(introducer, structuredField);
    }

    @Override
    public void handle(StructuredFieldIntroducer introducer) {
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
        case Data:
            structuredField = sfFactory.createData(introducer);
            break;
        case Position:
            structuredField = sfFactory.createPosition(introducer);
            break;
        case Include:
            structuredField = sfFactory.createInclude(introducer);
            break;
        case Control:
            structuredField = sfFactory.createControl(introducer);
            break;
        case Index:
            structuredField = sfFactory.createIndex(introducer);
            break;
        default:
            structuredField = null;
        }
        handle(introducer, structuredField);
    }

    private void handle(StructuredFieldIntroducer introducer, StructuredField structuredField) {
        // TODO remove UnhandledStructuredField when all structured fields can
        // be created
        creationHandler.handle(structuredField != null ? structuredField
                : new UnhandledStructuredField(introducer));
    }

    private static class UnhandledStructuredField extends AbstractStructuredField {
        private final StructuredFieldIntroducer introducer;

        public UnhandledStructuredField(StructuredFieldIntroducer introducer) {
            super(introducer);
            this.introducer = introducer;
        }

        @Override
        public String toString() {
            return "UNHANDLED SF: introducer " + introducer;
        }

        @Override
        public Map<String, String> getParameters() {
            return Collections.emptyMap();
        }
    }
}
