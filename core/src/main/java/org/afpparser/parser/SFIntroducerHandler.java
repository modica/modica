package org.afpparser.parser;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.SfTypeFactory.Begin;
import org.afpparser.afp.modca.SfTypeFactory.End;

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
     * Called after a SF introducer with type code {@link Begin} is parsed.
     *
     * @param sf the structured field introducer
     */
    void handleBegin(SfIntroducer sf);

    /**
     * Called after a SF introducer with type code {@link End} is parsed.
     *
     * @param sf the structured field introducer
     */
    void handleEnd(SfIntroducer sf);

    /**
     * Called after a SF introducer with a code other than {@link Begin} and {@link End} is parsed.
     *
     * @param sf the structured field introducer
     */
    void handle(SfIntroducer sf);
}
