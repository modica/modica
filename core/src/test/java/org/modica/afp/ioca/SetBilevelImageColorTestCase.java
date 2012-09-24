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

package org.modica.afp.ioca;

import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.NamedColor;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link SetBilevelImageColor}.
 */
public class SetBilevelImageColorTestCase {

    @Test
    public void testConstructor() {
        Parameters params = new Parameters(ByteUtils.createByteArray(4, 0, 0, 0, 0));
        SetBilevelImageColor sut = new SetBilevelImageColor(params);
        assertEquals(5, sut.getLength());
        assertEquals((byte) 0xF6, sut.getId());
        assertEquals(NamedColor.PRESENTATION_PROCESS_DEFAULT, sut.getColour());

        // test the rest of the colours
        testColour(NamedColor.BLUE, 0, 1);
        testColour(NamedColor.RED, 0, 2);
        testColour(NamedColor.MAGENTA_PINK, 0, 3);
        testColour(NamedColor.GREEN, 0, 4);
        testColour(NamedColor.CYAN_TURQUOISE, 0, 5);
        testColour(NamedColor.YELLOW, 0, 6);
        testColour(NamedColor.WHITE, 0, 7);
        testColour(NamedColor.BLACK, 0, 8);
        testColour(NamedColor.DARK_BLUE, 0, 9);
        testColour(NamedColor.ORANGE, 0, 10);
        testColour(NamedColor.PURPLE, 0, 11);
        testColour(NamedColor.DARK_GREEN, 0, 12);
        testColour(NamedColor.DARK_TURQUOISE, 0, 13);
        testColour(NamedColor.MUSTARD, 0, 14);
        testColour(NamedColor.GRAY, 0, 15);
        testColour(NamedColor.BROWN, 0, 16);

        testColour(NamedColor.BLUE, 0xFF, 1);
        testColour(NamedColor.RED, 0xFF, 2);
        testColour(NamedColor.MAGENTA_PINK, 0xFF, 3);
        testColour(NamedColor.GREEN, 0xFF, 4);
        testColour(NamedColor.CYAN_TURQUOISE, 0xFF, 5);
        testColour(NamedColor.YELLOW, 0xFF, 6);
        testColour(NamedColor.PRESENTATION_PROCESS_DEFAULT, 0xFF, 7);
        testColour(NamedColor.COLOUR_OF_MEDIUM, 0xFF, 8);
        testColour(NamedColor.PRESENTATION_PROCESS_DEFAULT, 0xFF, 0xFF);
    }

    private void testColour(NamedColor expected, int colourValue1, int colourValue2) {
        Parameters data = new Parameters(ByteUtils.createByteArray(4, 0, 0,
                colourValue1, colourValue2));
        SetBilevelImageColor sut = new SetBilevelImageColor(data);
        assertEquals(expected, sut.getColour());

        assertEquals(1, sut.getParameters().size());
        assertEquals("Colour", sut.getParameters().get(0).getKey());
        assertEquals(expected.toString(), sut.getParameters().get(0).getValue());
        assertEquals("SetBilevelImageColor", sut.getName());
    }
}
