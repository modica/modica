package org.afpparser.parser;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;

/**
 * 
 * This class consists exclusively of static methods that operate on or return
 * {@link SFIntroducerHandler}s.
 * 
 */
public final class SFIntroducerHandlers {

    private SFIntroducerHandlers() {
    }

    /**
     * Returns a SFIntroducerHandler that delegates to the handlers in their
     * sequential order.
     * 
     * @param handlers
     *            An array of SFIntroducerHandler
     * @return
     */
    public static SFIntroducerHandler aggregate(SFIntroducerHandler... handlers) {
        return new AggregateSFIntroducerHandler(handlers);
    }

    public static SFIntroducerHandler aggregate(SFIntroducerHandler[] handlerArray,
            SFIntroducerHandler... handlers) {
        SFIntroducerHandler[] all = new SFIntroducerHandler[handlerArray.length + handlers.length];
        System.arraycopy(handlerArray, 0, all, 0, handlerArray.length);
        System.arraycopy(handlers, 0, all, handlerArray.length, handlers.length);
        return aggregate(all);
    }

    private static class AggregateSFIntroducerHandler implements SFIntroducerHandler {

        private final List<SFIntroducerHandler> handlers;

        private AggregateSFIntroducerHandler(SFIntroducerHandler... handlers) {
            this.handlers = new ArrayList<SFIntroducerHandler>();
            for (SFIntroducerHandler handler : handlers) {
                if (handler instanceof AggregateSFIntroducerHandler) {
                    this.handlers.addAll(((AggregateSFIntroducerHandler) handler).handlers);
                } else {
                    this.handlers.add(handler);
                }
            }
        }

        @Override
        public void startAfp() {
            for (SFIntroducerHandler handler : handlers) {
                handler.startAfp();
            }
        }

        @Override
        public void endAfp() {
            for (SFIntroducerHandler handler : handlers) {
                handler.endAfp();
            }
        }

        @Override
        public void handleBegin(SfIntroducer sf) {
            for (SFIntroducerHandler handler : handlers) {
                handler.handleBegin(sf);
            }
        }

        @Override
        public void handleEnd(SfIntroducer sf) {
            for (SFIntroducerHandler handler : handlers) {
                handler.handleEnd(sf);
            }
        }

        @Override
        public void handle(SfIntroducer sf) {
            for (SFIntroducerHandler handler : handlers) {
                handler.handle(sf);
            }
        }

    }
}
