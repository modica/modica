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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.modica.afp.foca.FontFlag.BASE_LINE_OFFSET_SHIFT;
import static org.modica.afp.foca.FontFlag.COMPLETE_FONT;
import static org.modica.afp.foca.FontFlag.EXTENSION_FONT;
import static org.modica.afp.foca.FontFlag.MAGNETIC_INK_CHARACTER_RECOGNITION;
import static org.modica.afp.foca.FontFlag.NORMAL_PRINTING;
import static org.modica.afp.foca.FontFlag.UNIFORM_CHARACTER_BOX;
import static org.modica.afp.foca.FontFlag.VARIABLE_PATTERN_SIZE;

public class FontFlagTestCase {

    @Test
    public void testGetSetBits() {
        checkSetValue(1, MAGNETIC_INK_CHARACTER_RECOGNITION, COMPLETE_FONT, VARIABLE_PATTERN_SIZE);
        checkSetValue(2, NORMAL_PRINTING, EXTENSION_FONT, VARIABLE_PATTERN_SIZE);
        checkSetValue(16, NORMAL_PRINTING, COMPLETE_FONT, BASE_LINE_OFFSET_SHIFT, VARIABLE_PATTERN_SIZE);
        checkSetValue(64, NORMAL_PRINTING, COMPLETE_FONT, UNIFORM_CHARACTER_BOX);
    }

    private void checkSetValue(int bitField, FontFlag... flags) {
        checkEnumSet(bitField, EnumSet.of(flags[0], flags));
    }

    private void checkEnumSet(int bitField, EnumSet<FontFlag> flags) {
        assertEquals(flags, FontFlag.getSetBits((byte) bitField));
    }

    @Test
    public void testGetSetMultipleBits() {
        checkEnumSet(1 | 2, EnumSet.of(MAGNETIC_INK_CHARACTER_RECOGNITION, EXTENSION_FONT,
                VARIABLE_PATTERN_SIZE));

        checkEnumSet(16 | 64, EnumSet.of(NORMAL_PRINTING, COMPLETE_FONT, BASE_LINE_OFFSET_SHIFT,
                UNIFORM_CHARACTER_BOX));

        checkEnumSet(1 | 2 | 16 | 64, EnumSet.of(MAGNETIC_INK_CHARACTER_RECOGNITION,
                EXTENSION_FONT, BASE_LINE_OFFSET_SHIFT, UNIFORM_CHARACTER_BOX));
        checkEnumSet(0, EnumSet.of(NORMAL_PRINTING, COMPLETE_FONT, VARIABLE_PATTERN_SIZE));
    }
}
