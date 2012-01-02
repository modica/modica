package org.afpparser.parser;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * 
 * This class consists exclusively of static methods that operate on or return
 * {@link StructuredFieldIntroducerHandler}s.
 * 
 */
public final class StructuredFieldIntroducerHandlers {

    private StructuredFieldIntroducerHandlers() {
    }

    /**
     * Returns a SFIntroducerHandler that delegates to the handlers in their
     * sequential order.
     * 
     * @param handlers
     *            An array of SFIntroducerHandler
     * @return
     */
    public static StructuredFieldIntroducerHandler aggregate(StructuredFieldIntroducerHandler... handlers) {
        if (handlers.length == 1) {
            return handlers[0];
        } else {
            return new AggregateSFIntroducerHandler(handlers);
        }
    }

    public static StructuredFieldIntroducerHandler aggregate(StructuredFieldIntroducerHandler[] handlerArray,
            StructuredFieldIntroducerHandler... handlers) {
        StructuredFieldIntroducerHandler[] all = new StructuredFieldIntroducerHandler[handlerArray.length + handlers.length];
        System.arraycopy(handlerArray, 0, all, 0, handlerArray.length);
        System.arraycopy(handlers, 0, all, handlerArray.length, handlers.length);
        return aggregate(all);
    }

    private static class AggregateSFIntroducerHandler implements StructuredFieldIntroducerHandler {

        private final List<StructuredFieldIntroducerHandler> handlers;

        private AggregateSFIntroducerHandler(StructuredFieldIntroducerHandler... handlers) {
            this.handlers = new ArrayList<StructuredFieldIntroducerHandler>();
            for (StructuredFieldIntroducerHandler handler : handlers) {
                if (handler instanceof AggregateSFIntroducerHandler) {
                    this.handlers.addAll(((AggregateSFIntroducerHandler) handler).handlers);
                } else {
                    this.handlers.add(handler);
                }
            }
        }

        @Override
        public void startAfp() {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.startAfp();
            }
        }

        @Override
        public void endAfp() {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.endAfp();
            }
        }

        @Override
        public void handleBegin(StructuredFieldIntroducer sf) {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.handleBegin(sf);
            }
        }

        @Override
        public void handleEnd(StructuredFieldIntroducer sf) {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.handleEnd(sf);
            }
        }

        @Override
        public void handle(StructuredFieldIntroducer sf) {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.handle(sf);
            }
        }

    }
}
