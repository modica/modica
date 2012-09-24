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
 * Test case for {@link FontWidthClass}
 */
public class FontWidthClassTestCase {

    @Test
    public void testGetValue() {
        assertEquals(FontWidthClass.ULTRACONDENSED, FontWidthClass.getValue((byte) 1));
        assertEquals(FontWidthClass.EXTRACONDENSED, FontWidthClass.getValue((byte) 2));
        assertEquals(FontWidthClass.CONDENSED, FontWidthClass.getValue((byte) 3));
        assertEquals(FontWidthClass.SEMICONDENSED, FontWidthClass.getValue((byte) 4));
        assertEquals(FontWidthClass.MEDIUM, FontWidthClass.getValue((byte) 5));
        assertEquals(FontWidthClass.SEMIEXPANDED, FontWidthClass.getValue((byte) 6));
        assertEquals(FontWidthClass.EXPANDED, FontWidthClass.getValue((byte) 7));
        assertEquals(FontWidthClass.EXTRAEXPANDED, FontWidthClass.getValue((byte) 8));
        assertEquals(FontWidthClass.ULTRAEXPANDED, FontWidthClass.getValue((byte) 9));
    }
}
