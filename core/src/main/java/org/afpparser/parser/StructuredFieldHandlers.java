package org.afpparser.parser;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.StructuredField;

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
        return new AggregateStructuredFieldHandler(handlers);
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
