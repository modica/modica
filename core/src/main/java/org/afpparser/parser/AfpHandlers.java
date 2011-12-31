package org.afpparser.parser;

import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 * 
 * This class consists exclusively of static methods that operate on or return
 * {@link AfpHandler}s.
 * 
 */
public final class AfpHandlers {
    
    private AfpHandlers() {
    }
    
    /**
     * Delegate the handling of SF and introducer events to the supplied handlers.
     * 
     * @param sFIntroducerHandler Handles all introducer events.
     * @param structuredFieldHandler Handles all structure field events.
     * @return 
     */
    public static AfpHandler delegateTo(StructuredFieldIntroducerHandler sFIntroducerHandler,
            StructuredFieldHandler structuredFieldHandler) {
        return new DelegatingAfpHandler(sFIntroducerHandler, structuredFieldHandler);
    }

    private static class DelegatingAfpHandler implements AfpHandler {

        private final StructuredFieldIntroducerHandler sFIHandler;
        
        private final StructuredFieldHandler sFHandler;
        
        private DelegatingAfpHandler(StructuredFieldIntroducerHandler sFIHandler,
                StructuredFieldHandler sFHandler) {
            this.sFHandler = sFHandler;
            this.sFIHandler = sFIHandler;
        }
        
        @Override
        public void startAfp() {
            sFIHandler.startAfp();
        }

        @Override
        public void endAfp() {
            sFIHandler.endAfp();
        }

        @Override
        public void handleBegin(StructuredFieldIntroducer sf) {
            sFIHandler.handleBegin(sf);
        }

        @Override
        public void handleEnd(StructuredFieldIntroducer sf) {
            sFIHandler.handleEnd(sf);
        }

        @Override
        public void handle(StructuredFieldIntroducer sf) {
            sFIHandler.handle(sf);
        }
        
        @Override
        public void handle(StructuredField structuredField) {
            sFHandler.handle(structuredField);
        }
    }
}
