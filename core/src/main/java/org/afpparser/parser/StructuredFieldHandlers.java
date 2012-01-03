package org.afpparser.parser;

import java.util.ArrayList;
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

    public static StructuredFieldHandler aggregate(StructuredFieldHandler... handlers) {
        if (handlers.length == 0) {
            throw new IllegalArgumentException("Requires 1 or more  StructuredFieldHandlers");
        } else if ( handlers.length == 1) {
            return handlers[0];
        } else {
            return new AggregateStructuredFieldHandler(handlers);
        }
    }

    public static StructuredFieldHandler aggregate(StructuredFieldHandler[] handlerArray,
            StructuredFieldHandler... handlers) {
        StructuredFieldHandler[] all = new StructuredFieldHandler[handlerArray.length + handlers.length];
        System.arraycopy(handlerArray, 0, all, 0, handlerArray.length);
        System.arraycopy(handlers, 0, all, handlerArray.length, handlers.length);
        return aggregate(all);
    }

    private static class AggregateStructuredFieldHandler implements StructuredFieldHandler {

        private final List<StructuredFieldHandler> handlers;

        private AggregateStructuredFieldHandler(StructuredFieldHandler... handlers) {
            this.handlers = new ArrayList<StructuredFieldHandler>();
            for (StructuredFieldHandler handler : handlers) {
                if (handler instanceof AggregateStructuredFieldHandler) {
                    this.handlers.addAll(((AggregateStructuredFieldHandler) handler).handlers);
                } else {
                    this.handlers.add(handler);
                }
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
