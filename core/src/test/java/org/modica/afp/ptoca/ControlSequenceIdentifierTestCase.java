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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link ControlSequence}
 */
public class ControlSequenceIdentifierTestCase {

    @Test
    public void testAllControlSequences() {
        // Inline
        testControlSequence(0xC0, ControlSequenceIdentifier.SET_INLINE_MARGIN);
        testControlSequence(0xC2, ControlSequenceIdentifier.SET_INTERCHARACTER_ADJUSTMENT);
        testControlSequence(0xC4, ControlSequenceIdentifier.SET_VARIABLE_SPACE_CHARACTER_INCREMENT);
        testControlSequence(0xC6, ControlSequenceIdentifier.ABSOLUTE_MOVE_INLINE);
        testControlSequence(0xC8, ControlSequenceIdentifier.RELATIVE_MOVE_INLINE);
        // Baseline
        testControlSequence(0xD0, ControlSequenceIdentifier.SET_BASELINE_INCREMENT);
        testControlSequence(0xD2, ControlSequenceIdentifier.ABSOLUTE_MOVE_BASELINE);
        testControlSequence(0xD4, ControlSequenceIdentifier.RELATIVE_MOVE_BASELINE);
        testControlSequence(0xD8, ControlSequenceIdentifier.BEGIN_LINE);
        testControlSequence(0xF6, ControlSequenceIdentifier.SET_TEXT_ORIENTATION);
        // Controls for data strings
        testControlSequence(0xDA, ControlSequenceIdentifier.TRANSPARENT_DATA);
        testControlSequence(0xEE, ControlSequenceIdentifier.REPEAT_STRING);
        testControlSequence(0xF8, ControlSequenceIdentifier.NO_OPERATION);
        // Controls for rules
        testControlSequence(0xE4, ControlSequenceIdentifier.DRAW_I_AXIS_RULE);
        testControlSequence(0xE6, ControlSequenceIdentifier.DRAW_B_AXIS_RULE);
        // Character controls
        testControlSequence(0x74, ControlSequenceIdentifier.SET_TEXT_COLOR);
        testControlSequence(0x80, ControlSequenceIdentifier.SET_EXTENDED_TEXT_COLOR);
        testControlSequence(0xF0, ControlSequenceIdentifier.SET_CODED_FONT_LOCAL);
        testControlSequence(0xF2, ControlSequenceIdentifier.BEGIN_SUPPRESSION);
        testControlSequence(0xF4, ControlSequenceIdentifier.END_SUPPRESSION);
        // Field controls
        testControlSequence(0x72, ControlSequenceIdentifier.OVERSTRIKE);
        testControlSequence(0x76, ControlSequenceIdentifier.UNDERSCORE);
        testControlSequence(0x78, ControlSequenceIdentifier.TEMPORARY_BASELINE_MOVE);
    }

    private void testControlSequence(int id, ControlSequenceIdentifier csid) {
        assertEquals(csid, ControlSequenceIdentifier.getCsId((byte) id));
    }

}
