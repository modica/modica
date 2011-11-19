package org.afpparser.parser;

import org.afpparser.afp.modca.StructuredField;

/**
 * Implementations of this interface are registered with a
 * {@link StructuredFieldCreator} to publish structured field parsing events.
 */
public interface StructuredFieldHandler {
    void handle(StructuredField structuredField);
}
