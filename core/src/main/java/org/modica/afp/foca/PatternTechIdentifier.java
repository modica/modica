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

package org.modica.afp.foca;

import org.modica.common.ByteUtils;

/**
 * The Pattern Technology Identifier parameter specifies the technologies for the font graphic
 * patterns for this font. Pattern Technology is defined by implementing font products and is
 * documented in the font product documentation. This parameter is used in both font resources and
 * font references.
 */
public enum PatternTechIdentifier {
    /** Laser Matrix N-Bit Wide Horizontal Sections */
    LASER_MATRIX_N_BIT_WIDE(0x05),
    /** CID Keyed Outline Font Technology */
    CID_KEYED_FONT(0x1E),
    /** Type 1 PFB Outline Font Technology */
    PFB(0x1F);

    private final byte value;

    private PatternTechIdentifier(int value) {
        this.value = (byte) value;
    }
    
    /**
     * Return the value of the pattern tech identifier given a byte.
     *
     * @param value byte value
     * @return the pattern tech ID
     */
    public static PatternTechIdentifier getValue(byte value) {
        for (PatternTechIdentifier id : PatternTechIdentifier.values()) {
            if (id.value == value) {
                return id;
            }
        }
        throw new IllegalArgumentException("The Pattern Tech value is not recognised: "
                + ByteUtils.bytesToHex(value));
    }
}