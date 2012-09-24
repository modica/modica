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
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test case for {@link Overstrike}.
 */
public class OverstrikeTestCase extends ControlSequenceTestCase<Overstrike> {
    private Overstrike sut;
    private final byte[] data = ByteUtils.createByteArray(0x08, 1, 2);

    @Before
    public void setUp() throws UnsupportedEncodingException {
        Parameters params = new Parameters(data);
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.OVERSTRIKE;
        int length = 5;
        boolean isChained = false;

        sut = new Overstrike(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertTrue(sut.bypassRelativeMoveInline());
        assertFalse(sut.bypassAbsoluteMoveInline());
        assertFalse(sut.bypassSpaceChars());
        assertEquals(0x102, sut.getOverstrikeCharacter());

        String expectedString = "BypassRMI=true BypassAMI=false BypassSpaceChars=false";
        assertEquals(expectedString, sut.getValueAsString());
    }
}
