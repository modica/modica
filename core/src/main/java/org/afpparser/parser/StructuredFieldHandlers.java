package org.afpparser.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 *
 * This class consists exclusively of static methods that operate on or return
 * {@link StructuredFieldHandler}s.
 *
 */
public final class StructuredFieldHandlers {

    private StructuredFieldHandlers() {
    }

    public static StructuredFieldHandler chain(StructuredFieldHandler... handlers) {
        if (handlers.length == 0) {
            throw new IllegalArgumentException("Requires 1 or more  StructuredFieldHandlers");
        } else if ( handlers.length == 1) {
            return handlers[0];
        } else {
            return new ChainedStructuredFieldHandler(handlers);
        }
    }

    public static StructuredFieldHandler chain(Collection<StructuredFieldHandler> handlers) {
        return chain(handlers.toArray(new StructuredFieldHandler[0]));
    }

    private static class ChainedStructuredFieldHandler implements StructuredFieldHandler {

        private final List<StructuredFieldHandler> handlers;

        private ChainedStructuredFieldHandler(StructuredFieldHandler... handlers) {
            this.handlers = new ArrayList<StructuredFieldHandler>();
            for (StructuredFieldHandler handler : handlers) {
                if (handler instanceof ChainedStructuredFieldHandler) {
                    this.handlers.addAll(((ChainedStructuredFieldHandler) handler).handlers);
                } else {
                    this.handlers.add(handler);
                }
            }
        }

        @Override
        public void handleBegin(StructuredField structuredField) {
            for (StructuredFieldHandler handler : handlers) {
                handler.handleBegin(structuredField);
            }
        }

        @Override
        public void handleEnd(StructuredField structuredField) {
            for (StructuredFieldHandler handler : handlers) {
                handler.handleEnd(structuredField);
            }
        }

        @Override
        public void handle(StructuredField structuredField) {
            for (StructuredFieldHandler handler : handlers) {
                handler.handle(structuredField);
            }
        }
    }
}
