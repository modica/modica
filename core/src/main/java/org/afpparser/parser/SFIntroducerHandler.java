package org.afpparser.parser;

import org.afpparser.afp.modca.SfIntroducer;

public interface SFIntroducerHandler {

    /**
     * Called before the AFP document is parsed.
     * 
     * @param sf the structured field introducer
     */
    void startAfp();

    /**
     * Called after the AFP document is parsed.
     * 
     * @param sf the structured field introducer
     */
    void endAfp();

    /**
     * Called after a SF introducer with type code <code>Begin</code> is parsed.
     * 
     * @param sf the structured field introducer
     */
    void handleBegin(SfIntroducer sf);

    /**
     * Called after a SF introducer with type code <code>End</code> is parsed.
     * 
     * @param sf the structured field introducer
     */
    void handleEnd(SfIntroducer sf);

    /**
     * Called after a SF introducer with a code other than <code>Begin</code> and <code>End</code>
     * is parsed.
     * 
     * @param sf the structured field introducer
     */
    void handle(SfIntroducer sf);
}
