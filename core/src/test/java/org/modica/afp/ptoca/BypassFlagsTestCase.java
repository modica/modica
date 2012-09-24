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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link BypassFlags}.
 */
public class BypassFlagsTestCase {

    @Test
    public void testContructorAndGetterMethods() {
        testGetterMethods(false, false, false, 0);
        testGetterMethods(true, false, false, 0x08);
        testGetterMethods(false, true, false, 0x04);
        testGetterMethods(false, false, true, 0x02);
        testGetterMethods(false, false, false, 0x01);
        // No bypass in effect overrides all the others
        testGetterMethods(false, false, false, 0x0F);
    }

    private void testGetterMethods(boolean bypassRMI, boolean bypassAMI, boolean bypassSpace,
            int id) {
        BypassFlags sut = new BypassFlags((byte) id);
        assertEquals(bypassRMI, sut.bypassRelativeMoveInline());
        assertEquals(bypassAMI, sut.bypassAbsoluteMoveInline());
        assertEquals(bypassSpace, sut.bypassSpaceChars());
    }
}
