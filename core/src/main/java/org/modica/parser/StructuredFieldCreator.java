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

import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.StructuredFieldFactory;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * This class captures SFIntroduce creating events and delegates the creating of StructuredFields to
 * {@link StructuredFieldFactory} and publishes creation events to a {@link StructuredFieldHandler}
 * for further processing.
 */
public class StructuredFieldCreator implements StructuredFieldIntroducerHandler {

    private final StructuredFieldHandler creationHandler;

    private final StructuredFieldFactory sfFactory;

    /**
     * Create a new instance.
     *
     * @param structuredFieldFactory The factory for creating {@link StructuredField}s.
     * @param structuredFieldHandler The handler to publish creation events to.
     */
    public StructuredFieldCreator(StructuredFieldFactory structuredFieldFactory,
            StructuredFieldHandler structuredFieldHandler) {
        this.sfFactory = structuredFieldFactory;
        this.creationHandler = structuredFieldHandler;
    }

    @Override
    public void startAfp() {
        creationHandler.startAfp();
    }

    @Override
    public void endAfp() {
        creationHandler.endAfp();
    }

    @Override
    public void handleBegin(StructuredFieldIntroducer introducer) {
        StructuredField structuredField = sfFactory.createBegin(introducer);
        creationHandler.handleBegin(handleUnparsed(structuredField, introducer));
    }

    @Override
    public void handleEnd(StructuredFieldIntroducer introducer) {
        StructuredField structuredField = sfFactory.createEnd(introducer);
        creationHandler.handleEnd(handleUnparsed(structuredField, introducer));
    }

    private StructuredField handleUnparsed(StructuredField sf, StructuredFieldIntroducer intro) {
        return sf != null ? sf : new UnhandledStructuredField(intro);
    }

    @Override
    public void handle(StructuredFieldIntroducer introducer) {
        StructuredField structuredField = introducer.getType()
                                                    .getTypeCode()
                                                    .createField(sfFactory, introducer);

        // TODO remove UnhandledStructuredField once all structured fields can
        // be created
        creationHandler.handle(handleUnparsed(structuredField, introducer));
    }


    private static class UnhandledStructuredField extends AbstractStructuredField {
        private final StructuredFieldIntroducer introducer;

        public UnhandledStructuredField(StructuredFieldIntroducer introducer) {
            super(introducer);
            this.introducer = introducer;
        }

        @Override
        public String toString() {
            return "UNHANDLED SF: introducer " + introducer;
        }

        @Override
        public List<ParameterAsString> getParameters() {
            return Collections.emptyList();
        }
    }
}
