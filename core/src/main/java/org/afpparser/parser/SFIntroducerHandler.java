package org.afpparser.parser;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;

/**
 * Implementations of this interface are registered with a
 * {@link AFPDocumentParser} to publish structured field introducer parsing
 * events.
 */
public interface SFIntroducerHandler {

    /**
     * Called before the AFP document is parsed.
     * 
     * @param sf
     *            the structured field introducer
     */
    void startAfp();

    /**
     * Called after the AFP document is parsed.
     * 
     * @param sf
     *            the structured field introducer
     */
    void endAfp();

    /**
     * Called after a SF introducer with type code {@link Begin} is parsed.
     * 
     * @param sf
     *            the structured field introducer
     */
    void handleBegin(SfIntroducer sf);

    /**
     * Called after a SF introducer with type code {@link End} is parsed.
     * 
     * @param sf
     *            the structured field introducer
     */
    void handleEnd(SfIntroducer sf);

    /**
     * Called after a SF introducer with a code other than {@link Begin} and
     * {@link End} is parsed.
     * 
     * @param sf
     *            the structured field introducer
     */
    void handle(SfIntroducer sf);
}
