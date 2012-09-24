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

package org.modica.afp.modca.common;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test case for {@link GraphicalCharacterUseFlags}.
 */
public class GraphicalCharacterUseFlagsTestCase {

    @Test
    public void testFlagCheckingMethods() {
        byte flag = (byte) 0xE0;
        assertTrue(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = (byte) 0x80;
        assertTrue(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = (byte) 0x40;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = (byte) 0x20;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = (byte) 0x00;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));
    }
}
