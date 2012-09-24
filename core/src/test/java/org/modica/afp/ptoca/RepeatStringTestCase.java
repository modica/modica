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
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link RepeatString}.
 */
public class RepeatStringTestCase extends ControlSequenceTestCase<RepeatString> {
    private RepeatString sut;
    private RepeatString noString;
    private final String repeatedString = "This string is purely for test purposes.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        ByteBuffer bb = ByteBuffer.allocate(42);
        bb.put((byte) 0x01);
        bb.put((byte) 0x02);
        bb.put(repeatedString.getBytes("Cp500"));
        Parameters params = new Parameters(bb.array());
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.REPEAT_STRING;
        int length = 44;

        boolean isChained = false;

        sut = new RepeatString(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);

        // Test that when the control sequence length is < the length of the string, the string is
        // truncated
        length = 4;
        params.skipTo(0);
        noString = new RepeatString(expectedCsId, length, isChained, params);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(0x102, sut.getRepeatLength());
        assertEquals(repeatedString, sut.getRepeatDataString("Cp500"));
        String expectedString = "RepeatString=\"" + repeatedString + " to fill 258bytes";
        assertEquals(expectedString, sut.getValueAsString());

        assertEquals(258, sut.getRepeatLength());
        assertEquals("", noString.getRepeatDataString("Cp500"));
    }
}
