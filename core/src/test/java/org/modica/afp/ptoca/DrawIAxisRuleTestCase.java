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
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link DrawIAxisRule}.
 */
public class DrawIAxisRuleTestCase extends ControlSequenceTestCase<DrawIAxisRule> {

    private DrawIAxisRule sutWithWidth;
    private DrawIAxisRule sutWithoutWidth;

    private final byte[] dataArray = ByteUtils.createByteArray(1, 2, 3, 4, 0x80);

    @Before
    public void setUp() {
        Parameters params = new Parameters(dataArray);
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.DRAW_I_AXIS_RULE;
        int length = 7;
        boolean isChained = true;

        sutWithWidth = new DrawIAxisRule(expectedCsId, length, isChained, params);
        setMembers(sutWithWidth, expectedCsId, isChained, length);

        sutWithoutWidth = new DrawIAxisRule(expectedCsId, 4, isChained, new Parameters(dataArray));
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, sutWithWidth.getDrawLength());
        assertEquals(772.5, sutWithWidth.getDrawWidth(), 0.0000001);

        assertEquals(0x102, sutWithoutWidth.getDrawLength());
        assertEquals(0, (int) sutWithoutWidth.getDrawWidth());

        assertEquals("length=258 width=772.5", sutWithWidth.getValueAsString());
        assertEquals("length=258 width=0.0", sutWithoutWidth.getValueAsString());
    }
}
