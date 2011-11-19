package org.afpparser.parser;

import org.afpparser.afp.modca.SfIntroducer;

/**
 * A wrapper class that passes through all events to the delegate given in the constructor.
 */
public abstract class DelegatingSFIntroducerHandler implements SFIntroducerHandler {

    private final SFIntroducerHandler delegate;

    public DelegatingSFIntroducerHandler(SFIntroducerHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void startAfp() {
        delegate.startAfp();
    }

    @Override
    public void endAfp() {
        delegate.endAfp();
    }

    @Override
    public void handleBegin(SfIntroducer sf) {
        delegate.handleBegin(sf);
    }

    @Override
    public void handleEnd(SfIntroducer sf) {
        delegate.handleEnd(sf);
    }

    @Override
    public void handle(SfIntroducer sf) {
        delegate.handle(sf);
    }

}
