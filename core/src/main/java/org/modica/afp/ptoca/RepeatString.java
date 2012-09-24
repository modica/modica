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

import org.modica.afp.modca.Parameters;

/**
 * This control sequence specifies a string of bytes that is to be processed entirely as graphic
 * character code points. No code point is recognized as a Control Sequence Prefix. Current inline
 * position is incremented for each graphic character specified by a code point in the string. The
 * baseline position is not changed.
 */
public class RepeatString extends ControlSequence {

    private final int repeatLength;
    private final byte[] repeatData;

    public RepeatString(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        repeatLength = (int) params.getUInt(2);
        if (getLength() > 4) {
            repeatData = params.getByteArray(getLength() - 4);
        } else {
            repeatData = new byte[] {};
        }
    }

    /**
     * The length of bytes that the repeat data is repeated to fill.
     *
     * @return the repeat length
     */
    public int getRepeatLength() {
        return repeatLength;
    }

    /**
     * The data to repeat in the form of a String.
     *
     * @param encoding the encoding of the String
     * @return the String that is to be repeated
     * @throws UnsupportedEncodingException if an error occurs encoding the String.
     */
    public String getRepeatDataString(String encoding) throws UnsupportedEncodingException {
        return new String(repeatData, encoding);
    }

    @Override
    public String getValueAsString() {
        try {
            return "RepeatString=\"" + new String(repeatData, "Cp500") + " to fill "
                    + repeatLength + "bytes";
        } catch (UnsupportedEncodingException uee) {
            return "RepeatString to fill " + repeatLength + "bytes";
        }
    }
}
