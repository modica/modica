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

import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.types.BeginType;
import org.modica.afp.modca.structuredfields.types.EndType;

/**
 * Implementations of this interface are registered with a
 * {@link StructuredFieldIntroducerParser} to publish structured field introducer parsing
 * events.
 */
public interface StructuredFieldIntroducerHandler {

    /**
     * Called before the AFP document is parsed.
     * 
     * @param sf the structured field introducer
     */
    void startAfp();

    /**
     * Called after the AFP document is parsed.
     * 
     * @param sf the structured field introducer
     */
    void endAfp();

    /**
     * Called after a SF introducer with type code {@link BeginType} is parsed.
     * 
     * @param intro the structured field introducer
     */
    void handleBegin(StructuredFieldIntroducer intro);

    /**
     * Called after a SF introducer with type code {@link EndType} is parsed.
     * 
     * @param intro the structured field introducer
     */
    void handleEnd(StructuredFieldIntroducer intro);

    /**
     * Called after a SF introducer with a code other than {@link BeginType} and
     * {@link EndType} is parsed.
     * 
     * @param intro the structured field introducer
     */
    void handle(StructuredFieldIntroducer intro);
}
