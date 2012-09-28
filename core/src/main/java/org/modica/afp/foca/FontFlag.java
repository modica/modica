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

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * The flags that describe how a font is used.
 */
public enum FontFlag {
    NORMAL_PRINTING(0, false),
    /**
     * The MICR Font parameter indicates that this font resource was designed for use in Magnetic
     * Ink Character Recognition (MICR) applications.
     */
    MAGNETIC_INK_CHARACTER_RECOGNITION(0, true),
    COMPLETE_FONT(1, false),
    /**
     * The Extension Font parameter indicates that this font resource was designed to be an
     * extension (contains user-defined characters) to another font (a base font containing a set of
     * general-use characters).
     */
    EXTENSION_FONT(1, true),
    /**
     * (Retired) This bit was used by some implementations to determine whether the baseline offset
     * should be shifted by one pel or not. The original V1 unbounded box architecture counted pels
     * instead of the spaces between pels, thus no font could have a zero base- line offset. If this
     * bit is 0 (default), and the baseline offset is negative, most implementations will shift the
     * baseline offset value up by one pel. If this bit is 1, some implementations will not alter
     * the baseline offset value. This processing does not apply to fonts using relative units of
     * measure.
     */
    BASE_LINE_OFFSET_SHIFT(4, true),
    VARIABLE_PATTERN_SIZE(6, false),
    /**
     * The Uniform Character Box Font parameter specifies that the raster (bit-mapped) pattern data
     * for all the graphic characters of the font resource are of the same size. This parameter is
     * only valid if the “Pattern Technology Identifier” on page 106 indicates that the pattern data
     * is of a bit map technology. If this flag is off (0), the graphic character pattern boxes vary
     * in size. If this bit is on (1), all the character boxes in the font are of uniform height and
     * width, and the height and width for each box are taken from the Maximum Character Box Height
     * and Maximum Character Box Width parameters, respectively.
     */
    UNIFORM_CHARACTER_BOX(6, true);
    
    private final byte bitMask;
    private final boolean flagSet;
    
    private FontFlag(int bitNumber, boolean flagSet) {
        bitMask = (byte) (1 << bitNumber);
        this.flagSet = flagSet;
    }
    
    /**
     * Calculates which fields have been set from the bit field given.
     *
     * @param bitField the bit field
     * @return a set of flags that represet how the font is to be used
     */
    public static EnumSet<FontFlag> getSetBits(byte bitField) {
        // Check the reserved bits have been properly set
        assertBitCheck(bitField, 2, false);
        assertBitCheck(bitField, 3, false);
        assertBitCheck(bitField, 5, false);
        assertBitCheck(bitField, 7, false);
        Set<FontFlag> flags = new HashSet<FontFlag>();
        for (FontFlag flag : FontFlag.values()) {
            addToSet(flag, bitField, flags);
        }
        return EnumSet.copyOf(flags);
    }
    
    private static void assertBitCheck(byte bitField, int bitNumber, boolean set) {
        boolean check = (bitField & (1 << bitNumber)) > 0;
        assert check == set;
    }

    private static void addToSet(FontFlag flag, byte bitField, Set<FontFlag> set) {
        if (flag.flagSet && (flag.bitMask & bitField) > 0) {
            set.add(flag);
        } else if (!flag.flagSet && (flag.bitMask & bitField) == 0) {
            set.add(flag);
        }
    }
}