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

public enum UnitsPerUnitBase {
    NO_SHAPE_RESOLUTION(0x00, 0x00),
    RESOLUTION_240_PEL(0x09, 0x60),
    RESOLUTION_300_PEL(0x0B, 0xB8),
    RELATIVE_BASE(0x03, 0xE8);

    private final byte byte1Value;
    private final byte byte2Value;

    private UnitsPerUnitBase(int byte1, int byte2) {
        this.byte1Value = (byte) byte1;
        this.byte2Value = (byte) byte2;
    }

    public static UnitsPerUnitBase getValue(byte byte1, byte byte2) {
        for (UnitsPerUnitBase unit : UnitsPerUnitBase.values()) {
            if (unit.byte1Value == byte1 && unit.byte2Value == byte2) {
                return unit;
            }
        }
        throw new IllegalArgumentException("The units per unit base value is unrecognised: "
                + ByteUtils.bytesToHex(byte1, byte2));
    }
}
