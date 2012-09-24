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

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.types.BeginType;
import org.modica.afp.modca.structuredfields.types.EndType;

/**
 * Implementations of this interface are registered with a
 * {@link StructuredFieldCreator} to publish structured field parsing events.
 */
public interface StructuredFieldHandler {

    /** This method is invoked at the start of the AFP document. */
    void startAfp();

    /** Handles {@link BeginType} structured fields. */
    void handleBegin(StructuredField structuredField);

    /** Handles {@link EndType} structured fields. */
    void handleEnd(StructuredField structuredField);

    /** Handles all structured fields other than {@link BeginType} and {@link EndType}. */
    void handle(StructuredField structuredField);

    /** This methods is invoked once the AFP document has been parsed. */
    void endAfp();
}
