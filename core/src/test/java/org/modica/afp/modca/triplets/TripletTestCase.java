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

package org.modica.afp.modca.triplets;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public abstract class TripletTestCase<T> {
    private T x;
    private T y;
    private T z;
    private T notEqual;

    public abstract void setUp() throws UnsupportedEncodingException;

    public void setXYZ(T x, T y, T z, T notEqual) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.notEqual = notEqual;
    }

    @Test
    public void testEqualsHashCode() {
        // Consistency
        for (int i = 0; i < 100; i++) {
            // Reflexivity
            assertTrue(x.equals(x));
            assertTrue(y.equals(y));
            assertTrue(z.equals(z));

            // Symmetry
            assertTrue(x.equals(y));
            assertTrue(y.equals(x));
            assertFalse(x.equals(notEqual));
            assertFalse(notEqual.equals(x));

            // Transitivity
            assertTrue(x.equals(y));
            assertTrue(y.equals(z));
            assertTrue(x.equals(z));

            // Non-nullity
            assertFalse(x.equals(null));
            assertFalse(y.equals(null));
            assertFalse(z.equals(null));
        }
        assertEquals(x.hashCode(), y.hashCode());
        assertEquals(y.hashCode(), z.hashCode());
        assertNotSame(x.hashCode(), notEqual.hashCode());
    }

    public abstract void testGetParameters();

    public void parameterTester(List<ParameterAsString> expectedParams, Triplet triplet) {
        List<ParameterAsString> actualParams = triplet.getParameters();
        for (int i = 0; i < expectedParams.size(); i++) {
            assertEquals(expectedParams.get(i).getKey(), actualParams.get(i).getKey());
            assertEquals(expectedParams.get(i).getValue(), actualParams.get(i).getValue());
        }
    }
}
