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

package org.modica.afp.ptoca;

import java.io.UnsupportedEncodingException;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;

/**
 * This control sequence specifies a string of code points, all of which are to be processed as
 * graphic characters. No code point within the data field is recognised as a Control Sequence
 * Prefix. The current inline position is incremented for each graphic character in the string.
 */
public class TransparentData extends ControlSequence {

    private final String data;

    public TransparentData(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params, Context ctx) throws UnsupportedEncodingException {
        super(csId, length, isChained);
        data = params.getString(length - 2, ctx.getPTXEncoding());
    }

    /**
     * Return the data in the form of a String.
     *
     * @param encoding the encoding to encode the byte array
     * @return the String form
     * @throws UnsupportedEncodingException if an error occurs encoding the String
     */
    public String getDataString(String encoding) throws UnsupportedEncodingException {
        return data;
    }

    @Override
    public String getValueAsString() {
        try {
            return "\"" + getDataString("Cp500") + "\"";
        } catch (UnsupportedEncodingException uee) {
            return "TransparentData:";
        }
    }
}
