package org.afpparser.parser;

import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.StructuredFieldFactory;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;

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
        creationHandler.startAfp();
    }

    @Override
    public void endAfp() {
        creationHandler.endAfp();
    }

    @Override
    public void handleBegin(StructuredFieldIntroducer introducer) {
        StructuredField structuredField = sfFactory.createBegin(introducer);
        creationHandler.handleBegin(structuredField);
    }

    @Override
    public void handleEnd(StructuredFieldIntroducer introducer) {
        StructuredField structuredField = sfFactory.createEnd(introducer);
        creationHandler.handleEnd(structuredField);
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

        // TODO remove UnhandledStructuredField once all structured fields can
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
        public List<ParameterAsString> getParameters() {
            return Collections.emptyList();
        }
    }
}
