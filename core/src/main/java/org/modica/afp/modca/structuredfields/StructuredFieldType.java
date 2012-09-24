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

package org.modica.afp.modca.structuredfields;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField.Builder;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * This represents type of structured field as defined in the AFP specifications (MODCA, FOCA etc).
 */
public interface StructuredFieldType {

    /**
     * The category code.
     * @return the category
     */
    CategoryCode getCategoryCode();

    /**
     * The type code of this structured field type.
     *
     * @return the type code
     */
    TypeCode getTypeCode();

    /**
     * The name of this structured field type.
     *
     * @return the name
     */
    String getName();

    /**
     * Delegates to the appropriate method on the handler for dealing with this structured field.
     *
     * @param handler the handler
     */
    void handleIntroducer(StructuredFieldIntroducerHandler handler, StructuredFieldIntroducer intro);

    /**
     * The builder for the structured field of this type.
     *
     * @return structured field builder
     */
    Builder getBuilder();

    public final static class NotYetImplementedBuilder implements Builder {
        @Override
        public StructuredField build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return null;
        }
    }
}
