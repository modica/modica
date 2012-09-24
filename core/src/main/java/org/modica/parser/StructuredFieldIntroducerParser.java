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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * This class reads from a fileInputStream, identifying structured field introducers, creating the
 * light-weight {@link StructuredFieldIntroducer} and delegating further processing to a
 * {@link StructuredFieldIntroducerHandler}.
 */
public final class StructuredFieldIntroducerParser {

    private final Iterable<StructuredFieldIntroducer> introducers;

    private final StructuredFieldIntroducerHandler handler;

    /**
     * @param afpFileInputStream An AFP file stream.
     * @param handler An SFIntroducerHandler to handle {@link StructuredFieldIntroducer} parsing
     * events.
     * @throws FileNotFoundException Thrown if the AFP file is invalid.
     */
    public StructuredFieldIntroducerParser(FileInputStream afpFileInputStream,
            StructuredFieldIntroducerHandler handler)
            throws FileNotFoundException {
        this(new StructuredFieldIntroducerReader(afpFileInputStream), handler);
    }

    /**
     * @param introducerProducer iterable over {@link StructuredFieldIntroducer}.
     * @param handler An SFIntroducerHandler to handle {@link StructuredFieldIntroducer} parsing
     * events.
     */
    public StructuredFieldIntroducerParser(Iterable<StructuredFieldIntroducer> introducerProducer,
            StructuredFieldIntroducerHandler handler) {
        this.introducers = introducerProducer;
        this.handler = handler;
    }

    /**
     * Iterates through the StructruredFieldIntroducers parsed by the reader
     * and dispatches them to the handler.
     * 
     * @throws IOException
     */
    public void parse() throws IOException {
        handler.startAfp();
        for (StructuredFieldIntroducer intro : introducers) {
            intro.handleIntroducer(handler);
        }
        handler.endAfp();
    }
}
