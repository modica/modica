package org.afpparser.parser;

import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 * Implementations of this interface are registered with a
 * {@link StructuredFieldCreator} to publish structured field parsing events.
 */
public interface StructuredFieldHandler {

    void startAfp();

    void handleBegin(StructuredField structuredField);

    void handleEnd(StructuredField structuredField);

    void handle(StructuredField structuredField);

    void endAfp();
}
