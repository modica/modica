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

import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * This class consists exclusively of static methods that operate on or return
 * {@link StructuredFieldIntroducerHandler}s.
 */
public final class StructuredFieldIntroducerHandlers {

    private StructuredFieldIntroducerHandlers() {
    }

    /**
     * Returns a SFIntroducerHandler that delegates to the handlers in their
     * sequential order.
     *
     * @param handlers
     *            An array of SFIntroducerHandler
     * @return
     */
    public static StructuredFieldIntroducerHandler chain(StructuredFieldIntroducerHandler... handlers) {
        if (handlers.length == 0) {
            throw new IllegalArgumentException("Requires 1 or more  StructuredFieldIntroducerHandlers");
        } else if (handlers.length == 1) {
            return handlers[0];
        } else {
            return new ChainedSFIntroducerHandler(handlers);
        }
    }


    public static StructuredFieldIntroducerHandler chain(Collection<StructuredFieldIntroducerHandler> handlers) {
        return chain(handlers.toArray(new StructuredFieldIntroducerHandler[0]));
    }


    public static final class ChainedSFIntroducerHandler implements StructuredFieldIntroducerHandler {

        private final List<StructuredFieldIntroducerHandler> handlers;

        private ChainedSFIntroducerHandler(StructuredFieldIntroducerHandler... handlers) {
            this.handlers = new ArrayList<StructuredFieldIntroducerHandler>();
            for (StructuredFieldIntroducerHandler handler : handlers) {
                if (handler instanceof ChainedSFIntroducerHandler) {
                    this.handlers.addAll(((ChainedSFIntroducerHandler) handler).handlers);
                } else {
                    this.handlers.add(handler);
                }
            }
        }

        @Override
        public void startAfp() {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.startAfp();
            }
        }

        @Override
        public void endAfp() {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.endAfp();
            }
        }

        @Override
        public void handleBegin(StructuredFieldIntroducer sf) {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.handleBegin(sf);
            }
        }

        @Override
        public void handleEnd(StructuredFieldIntroducer sf) {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.handleEnd(sf);
            }
        }

        @Override
        public void handle(StructuredFieldIntroducer sf) {
            for (StructuredFieldIntroducerHandler handler : handlers) {
                handler.handle(sf);
            }
        }

    }
}
