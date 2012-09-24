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
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test case for {@link Rotation}.
 */
public class RotationTestCase {

    @Test
    public void testGetters() {
        testBytes(0, Rotation.ZERO);
        testBytes(0x2D, Rotation.NINETY);
        testBytes(0x5A, Rotation.ONE_EIGHTY);
        testBytes(0x87, Rotation.TWO_SEVENTY);

        try {
            Rotation.getValue((byte) 0x10);
            fail("Exception should be thrown with invalid Rotation value");
        } catch (IllegalArgumentException iae) {
            //Pass
        }
    }

    private void testBytes(int val, Rotation rot) {
        assertArrayEquals(ByteUtils.createByteArray(val, 0), rot.getBytes());
        assertEquals(rot, Rotation.getValue((byte) val));
    }

}
