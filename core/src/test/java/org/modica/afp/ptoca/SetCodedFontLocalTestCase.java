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
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.Parameters;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link SetCodedFontLocal}.
 */
public class SetCodedFontLocalTestCase extends ControlSequenceTestCase<SetCodedFontLocal> {
    private SetCodedFontLocal sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { (byte) 0x31 });
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_CODED_FONT_LOCAL;
        int length = 3;
        boolean isChained = true;

        sut = new SetCodedFontLocal(expectedCsId, length, isChained, params, new ContextImpl());
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x31, sut.getLocalId());

        assertEquals("localid=0x31", sut.getValueAsString());
    }
}
