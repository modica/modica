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

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.ColorSpace;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link SetExtendedTextColor}.
 */
public class SetExtendedTextColorTestCase extends ControlSequenceTestCase<SetExtendedTextColor> {
    private SetExtendedTextColor sut;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        byte[] data = ByteUtils.createByteArray(0, 1, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8);
        Parameters params = new Parameters(data);
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_EXTENDED_TEXT_COLOR;
        int length = 16;

        boolean isChained = false;

        sut = new SetExtendedTextColor(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(ColorSpace.RGB, sut.getColourSpace());
        assertEquals(1, sut.getColourSize1());
        assertEquals(2, sut.getColourSize2());
        assertEquals(3, sut.getColourSize3());
        assertEquals(4, sut.getColourSize4());
        assertEquals(0x5060708L, sut.getColor());

        String expectedStr = "ColourSpace=" + ColorSpace.RGB.toString()
                + " colSize1=" + 1
                + " colSize2=" + 2
                + " colSize3=" + 3
                + " colSize4=" + 4;
        assertEquals(expectedStr, sut.getValueAsString());
    }
}
