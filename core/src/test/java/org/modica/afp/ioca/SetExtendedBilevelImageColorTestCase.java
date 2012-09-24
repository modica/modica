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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.ColorSpace;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link SetExtendedBilevelImageColor}.
 */
public class SetExtendedBilevelImageColorTestCase {

    @Test
    public void testConstructor() {
        Parameters params = new Parameters(ByteUtils.createByteArray(11,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        SetExtendedBilevelImageColor sut = new SetExtendedBilevelImageColor(params);
        assertEquals(12, sut.getLength());
        assertEquals(ColorSpace.RGB, sut.getColourSpace());
        assertEquals(0x203, sut.getColourSize1());
        assertEquals(0x405, sut.getColourSize2());
        assertEquals(0x607, sut.getColourSize3());
        assertEquals(0x809, sut.getColourSize4());
        assertEquals(0xA0B, sut.getColor());
        assertEquals("SetExtendedBilevelImageColour", sut.getName());

        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ColourSpace", ColorSpace.RGB));
        expectedParams.add(new ParameterAsString("colourSize1", 0x203));
        expectedParams.add(new ParameterAsString("colourSize2", 0x405));
        expectedParams.add(new ParameterAsString("colourSize3", 0x607));
        expectedParams.add(new ParameterAsString("colourSize4", 0x809));
        for (int i = 0; i < expectedParams.size(); i++) {
            assertEquals(expectedParams.get(i).getKey(), sut.getParameters().get(i).getKey());
            assertEquals(expectedParams.get(i).getValue(), sut.getParameters().get(i).getValue());
        }
    }
}
