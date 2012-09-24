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
import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransparentDataTestCase extends ControlSequenceTestCase<TransparentData> {

    private TransparentData sut;
    private final String testString = "This is a string purely used for test purposes.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        Parameters params = new Parameters(testString.getBytes("Cp500"));
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.TRANSPARENT_DATA;
        int length = 49;
        boolean isChained = true;
        Context ctx = mock(Context.class);
        when(ctx.getPTXEncoding()).thenReturn(500);
        sut = new TransparentData(expectedCsId, length, isChained, params, ctx);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(testString, sut.getDataString("Cp500"));
        assertEquals("\"" + testString + "\"", sut.getValueAsString());
    }
}
