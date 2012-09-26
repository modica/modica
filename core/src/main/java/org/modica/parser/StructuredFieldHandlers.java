/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.modica.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modica.afp.modca.structuredfields.StructuredField;

/**
 * This class consists exclusively of static methods that operate on or return
 * {@link StructuredFieldHandler}s.
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
        public void startAfp() {
            for (StructuredFieldHandler handler : handlers) {
                handler.startAfp();
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

        @Override
        public void endAfp() {
            for (StructuredFieldHandler handler : handlers) {
                handler.endAfp();
            }
        }

    }

    public static StructuredFieldHandler ignore() {
        return IgnoringStructuredFieldHandler.SINGLETON;
    }

    private static class IgnoringStructuredFieldHandler implements StructuredFieldHandler {

        private static final StructuredFieldHandler SINGLETON = new IgnoringStructuredFieldHandler();

        @Override
        public void startAfp() {
        }

        @Override
        public void handleBegin(StructuredField structuredField) {
        }

        @Override
        public void handleEnd(StructuredField structuredField) {
        }

        @Override
        public void handle(StructuredField structuredField) {
        }

        @Override
        public void endAfp() {
        }
    }
}
