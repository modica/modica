package org.modica.parser;

import org.modica.afp.modca.structuredfields.BeginType;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.End;

/**
 * Implementations of this interface are registered with a
 * {@link StructuredFieldIntroducerParser} to publish structured field introducer parsing
 * events.
 */
public interface StructuredFieldIntroducerHandler {

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
     * Called after a SF introducer with type code {@link BeginType} is parsed.
     * 
     * @param sf
     *            the structured field introducer
     */
    void handleBegin(StructuredFieldIntroducer sf);

    /**
     * Called after a SF introducer with type code {@link End} is parsed.
     * 
     * @param sf
     *            the structured field introducer
     */
    void handleEnd(StructuredFieldIntroducer sf);

    /**
     * Called after a SF introducer with a code other than {@link BeginType} and
     * {@link End} is parsed.
     * 
     * @param sf
     *            the structured field introducer
     */
    void handle(StructuredFieldIntroducer sf);
}
