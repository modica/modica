package org.afpparser.parser;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.SfIntroducer;

public final class AggregateSFIntroducerHandler implements SFIntroducerHandler {

    private final List<SFIntroducerHandler> handlers;

    private AggregateSFIntroducerHandler(SFIntroducerHandler... handlers) {
        this.handlers = new ArrayList<SFIntroducerHandler>();
        for (SFIntroducerHandler handler : handlers) {
            if (handler instanceof AggregateSFIntroducerHandler) {
                this.handlers
                        .addAll(((AggregateSFIntroducerHandler) handler).handlers);
            } else {
                this.handlers.add(handler);
            }
        }
    }

    public static SFIntroducerHandler aggregate(SFIntroducerHandler... handlers) {
        return new AggregateSFIntroducerHandler(handlers);
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
