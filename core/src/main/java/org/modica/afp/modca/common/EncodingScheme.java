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

package org.modica.afp.modca.common;

/**
 * This parameter identifies the code page as either EBCDIC-Presentation encoded, IBM-PC-Data
 * (ASCII) encoded, or UCS-Presentation encoded. It also specifies the code points as either fixed
 * single-byte values or fixed double-byte values.
 */
public enum EncodingScheme {
    NO_ENCODING(0x00),
    SINGLE_BYTE_NO_ENCODING(0x01),
    DOUBLE_BYTE_NO_ENCODING(0x02),
    SINGLE_BYTE_IBMPC_DATA(0x21),
    SINGLE_BYTE_EBCDIC(0x61),
    DOUBLE_BYTE_EBCDIC(0x62),
    DOUBLE_BYTE_UCS(0x82);

    private final byte id;

    private EncodingScheme(int id) {
        this.id = (byte) id;
    }

    public static EncodingScheme getValue(byte id) {
        for (EncodingScheme enc : EncodingScheme.values()) {
            if (enc.id ==  id) {
                return enc;
            }
        }
        throw new IllegalArgumentException(id + " is not a valid Encoding Scheme");
    }
}
