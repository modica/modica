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
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;

/**
 * A structured field is the building block of an AFP document.
 */
public interface StructuredField {
    /**
     * Whether or not the extensions data flag is set in the introducer.
     *
     * @return true if the extension data flag is set
     */
    boolean hasExtData();

    /**
     * Whether or not the segmented data flag is set in the introducer.
     *
     * @return true if the segmented flag is set
     */
    boolean hasSegmentedData();

    /**
     * Whether or not the data padding flag is set in the introducer.
     *
     * @return true if the data padding flag is set
     */
    boolean hasDataPadding();

    /**
     * The length of this structured field, this does not include extension data.
     *
     * @return the length
     */
    int getLength();

    /**
     * The type of structured field this object represents.
     *
     * @return structured field type
     */
    StructuredFieldType getType();

    /**
     * The byte offset of this structured field within the AFP document.
     *
     * @return the byte offset
     */
    long getOffset();

    /**
     * The length of extension data.
     *
     * @return the ext data length
     */
    int getExtLength();

    /**
     * The number of bytes until the next structured field, this is basically the sum of the ext
     * data length and the structured field length.
     *
     * @return the number of bytes to the next structured field
     */
    int bytesToNextStructuredField();

    /**
     * Returns a map of parameter names with their corresponding values.
     *
     * @return parameters and their values
     */
    List<ParameterAsString> getParameters();

    public interface Builder {
        StructuredField build(StructuredFieldIntroducer intro, Parameters params, Context context)
                throws UnsupportedEncodingException, MalformedURLException;
    }
}
