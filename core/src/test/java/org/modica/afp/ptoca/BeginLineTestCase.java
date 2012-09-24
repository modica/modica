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

import static org.junit.Assert.assertEquals;

public class BeginLineTestCase extends ControlSequenceTestCase<BeginLine> {

    private BeginLine sut;

    @Before
    public void setUp() {
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.BEGIN_LINE;
        boolean isChained = false;
        int length = 3;
        sut = new BeginLine(expectedCsId, length, isChained);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals("", sut.getValueAsString());
    }
}
