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

package org.modica.afp.foca;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link FontWeightClass}.
 */
public class FontWeightClassTestCase {

    @Test
    public void testGetValue() {
        assertEquals(FontWeightClass.ULTRALIGHT, FontWeightClass.getValue((byte) 1));
        assertEquals(FontWeightClass.EXTRALIGHT, FontWeightClass.getValue((byte) 2));
        assertEquals(FontWeightClass.LIGHT, FontWeightClass.getValue((byte) 3));
        assertEquals(FontWeightClass.SEMILIGHT, FontWeightClass.getValue((byte) 4));
        assertEquals(FontWeightClass.MEDIUM, FontWeightClass.getValue((byte) 5));
        assertEquals(FontWeightClass.SEMIBOLD, FontWeightClass.getValue((byte) 6));
        assertEquals(FontWeightClass.BOLD, FontWeightClass.getValue((byte) 7));
        assertEquals(FontWeightClass.EXTRABOLD, FontWeightClass.getValue((byte) 8));
        assertEquals(FontWeightClass.ULTRABOLD, FontWeightClass.getValue((byte) 9));
    }
}
