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

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.NamedColor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test case for {@link SetTextColor}.
 */
public class SetTextColorTestCase extends ControlSequenceTestCase<SetTextColor> {

    private SetTextColor sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { 0, 0x02, 1 });
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_TEXT_COLOR;
        int length = 4;
        boolean isChained = true;

        sut = new SetTextColor(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);

        length = 5;
        params = new Parameters(new byte[] { 0, 0x33, 1 });
        try {
            new SetTextColor(expectedCsId, length, isChained, params);
            fail("Precision has been set with an invalid colour, an exception is expected");
        } catch (IllegalStateException ise) {
            //Pass
        }
    }

    @Test
    public void testGetterMethods() {
        assertEquals(NamedColor.RED, sut.getForegroundColour());
        assertEquals(NamedColor.RED.toString(), sut.getValueAsString());

        assertEquals(NamedColor.RED, sut.getForegroundColour());
        assertEquals(NamedColor.RED.toString(), sut.getValueAsString());
    }
}