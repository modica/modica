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

package org.modica.afp.modca;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.descriptor.CodePageDescriptor;

/**
 * This class provides context for structured fields. Some fields rely on parameters in other
 * structured fields to contextualize their purpose, this is an interface for providing that
 * context. The contextual data is used only to parse and instantiate the {@link StructuredField}.  
 */
public interface Context {

    /**
     * An enumeration of the different types of objects that can be stored in this Context object.
     */
    public enum ContextType {
        FOCA_CPI_REPEATING_GROUP_LENGTH,
        MODCA_GCSGID,
        MODCA_MAP_CODED_FONT,
        PTOCA_SET_CODED_FONT_LOCAL
    }

    /**
     * Put a context item into the context map.
     *
     * @param the context item
     * @param obj the value of the item
     */
    void put(ContextType focaContext, Object obj);

    /**
     * Get a contextual item.
     *
     * @param contextType the context item
     * @return the value of the item
     */
    Object get(ContextType focaContext);

    void setCpgidForCodePage(CodePageDescriptor descriptor);

    void setCurrentCodePageName(String name);

    int getPTXEncoding();

    void endCodePage();
}
