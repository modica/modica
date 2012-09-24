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
 * Specifies the clockwise character rotation relative to the character coordinate system.
 */
public enum Rotation {
    ZERO(0x00),
    NINETY(0x2D),
    ONE_EIGHTY(0x5A),
    TWO_SEVENTY(0x87);

    private final byte value;

    private Rotation(int byteValue) {
        this.value = (byte) byteValue;
    }

    /**
     * Get the identifying byte array.
     *
     * @return the bytes identifying this character rotation
     */
    public byte[] getBytes() {
        return new byte[] { value, 0x00 };
    }

    /**
     * Returns the character rotation as an enumerated type.
     *
     * @param identifier the byte value
     * @return an enumeration of character rotation
     */
    public static Rotation getValue(byte identifier) {
        for (Rotation charRotation : Rotation.values()) {
            if (charRotation.value == identifier) {
                return charRotation;
            }
        }
        throw new IllegalArgumentException(
                identifier + " is not a valid CharacterRotation value");
    }
}
