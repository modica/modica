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

package org.modica.afp.modca;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterAsStringTestCase {

    @Test
    public void testKeyValue() {
        ParameterAsString sut = new ParameterAsString("key", "Value");
        assertEquals("key", sut.getKey());
        assertEquals("Value", sut.getValue());
    }

    public static void testParameters(List<ParameterAsString> expected,
            List<ParameterAsString> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getKey(), actual.get(i).getKey());
            assertEquals(expected.get(i).getValue(), actual.get(i).getValue());
        }
    }
}
