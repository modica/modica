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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test case for {@link SetIntercharacterAdjustment}.
 */
public class SetIntercharacterAdjustmentTestCase
        extends ControlSequenceTestCase<SetIntercharacterAdjustment> {

    private SetIntercharacterAdjustment sut;
    private SetIntercharacterAdjustment negativeDirection;
    private SetIntercharacterAdjustment noDirection;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { 0x01, 0x02, 0x00 });
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_INTERCHARACTER_ADJUSTMENT;
        int length = 5;
        boolean isChained = true;

        sut = new SetIntercharacterAdjustment(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);

        params = new Parameters(new byte[] { 0x01, 0x02, 0x01 });
        negativeDirection = new SetIntercharacterAdjustment(expectedCsId, length, isChained, params);

        length = 4;
        params = new Parameters(new byte[] { 0x01, 0x02, 0x01 });
        noDirection = new SetIntercharacterAdjustment(expectedCsId, length, isChained, params);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, sut.getAdjustment());
        assertTrue(sut.isDirectionPositive());
        assertEquals("move 258", sut.getValueAsString());

        assertEquals(0x102, negativeDirection.getAdjustment());
        assertFalse(negativeDirection.isDirectionPositive());
        assertEquals("move 258 negative direction", negativeDirection.getValueAsString());

        assertEquals(0x102, noDirection.getAdjustment());
        assertTrue(noDirection.isDirectionPositive());
        assertEquals("move 258", noDirection.getValueAsString());
    }
}
