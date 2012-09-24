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

import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link NamedColor}.
 */
public class NamedColorTestCase {

    @Test
    public void testGetters() {
        testNamedColor(0, 0, NamedColor.PRESENTATION_PROCESS_DEFAULT);
        testNamedColor(0, 1, NamedColor.BLUE);
        testNamedColor(0, 2, NamedColor.RED);
        testNamedColor(0, 3, NamedColor.MAGENTA_PINK);
        testNamedColor(0, 4, NamedColor.GREEN);
        testNamedColor(0, 5, NamedColor.CYAN_TURQUOISE);
        testNamedColor(0, 6, NamedColor.YELLOW);
        testNamedColor(0, 7, NamedColor.WHITE);
        testNamedColor(0, 8, NamedColor.BLACK);
        testNamedColor(0, 9, NamedColor.DARK_BLUE);
        testNamedColor(0, 0xA, NamedColor.ORANGE);
        testNamedColor(0, 0xB, NamedColor.PURPLE);
        testNamedColor(0, 0xC, NamedColor.DARK_GREEN);
        testNamedColor(0, 0xD, NamedColor.DARK_TURQUOISE);
        testNamedColor(0, 0xE, NamedColor.MUSTARD);
        testNamedColor(0, 0xF, NamedColor.GRAY);
        testNamedColor(0, 0x10, NamedColor.BROWN);

        testNamedColor(0xFF, 0, NamedColor.PRESENTATION_PROCESS_DEFAULT);
        testNamedColor(0xFF, 1, NamedColor.BLUE);
        testNamedColor(0xFF, 2, NamedColor.RED);
        testNamedColor(0xFF, 3, NamedColor.MAGENTA_PINK);
        testNamedColor(0xFF, 4, NamedColor.GREEN);
        testNamedColor(0xFF, 5, NamedColor.CYAN_TURQUOISE);
        testNamedColor(0xFF, 6, NamedColor.YELLOW);
        testNamedColor(0xFF, 7, NamedColor.PRESENTATION_PROCESS_DEFAULT);
        testNamedColor(0xFF, 8, NamedColor.COLOUR_OF_MEDIUM);
        testNamedColor(0xFF, 0xFF, NamedColor.PRESENTATION_PROCESS_DEFAULT);

        testNamedColor(1, 2, null);
    }

    private void testNamedColor(int byte1, int byte2, NamedColor expected) {
        byte[] bytes = ByteUtils.createByteArray(byte1, byte2);
        NamedColor sut = NamedColor.getValue(new Parameters(bytes));
        assertEquals(expected, sut);
    }

}
