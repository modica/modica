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
 * This control sequence specifies a string of bytes that are to be ignored.
 */
public class NoOperation extends ControlSequence {

    private final byte[] bytes;

    public NoOperation(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        bytes = params.getByteArray(getLength() - 2);
    }

    /**
     * The string of bytes this NoOp wraps in String form.
     *
     * @param encoding the encoding for the bytes
     * @return the String form
     * @throws UnsupportedEncodingException if an encoding error occurs
     */
    public String getCommentAsString(String encoding) throws UnsupportedEncodingException {
        return new String(bytes, encoding);
    }

    @Override
    public String getValueAsString() {
        try {
            return new String(bytes, "Cp500");
        } catch (UnsupportedEncodingException uee) {
            return "";
        }
    }

}
