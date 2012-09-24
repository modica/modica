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

package org.modica.afp.ioca;

import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test case for {@link IocaFunctionSetId}
 */
public class IocaFunctionSetIdTestCase {

    @Test
    public void testConstructor() {
        Parameters params = new Parameters(ByteUtils.createByteArray(0x02, 0x01, 0x0A));
        IocaFunctionSetId sut = new IocaFunctionSetId(params);
        assertEquals(FunctionSet.FS_10, sut.getFunctionSet());
        assertEquals(3, sut.getLength());
        assertEquals((byte) 0xF7, sut.getId());

        // test other Function set values
        testFunctionSetGetter(FunctionSet.FS_11, 0x0B);
        testFunctionSetGetter(FunctionSet.FS_40, 0x28);
        testFunctionSetGetter(FunctionSet.FS_42, 0x2A);
        testFunctionSetGetter(FunctionSet.FS_45, 0x2D);

        try {
            testFunctionSetGetter(FunctionSet.FS_10, 0x11);
            fail("Should throw an exception if an illegal FunctionSet id is given.");
        } catch (IllegalArgumentException iae) {
            // Pass
        }
    }

    private void testFunctionSetGetter(FunctionSet expected, int id) {
        Parameters params = new Parameters(ByteUtils.createByteArray(0x02, 0x01, id));
        IocaFunctionSetId sut = new IocaFunctionSetId(params);
        assertEquals(expected, sut.getFunctionSet());

        assertEquals(1, sut.getParameters().size());
        assertEquals("FunctionSet", sut.getParameters().get(0).getKey());
        assertEquals(expected.toString(), sut.getParameters().get(0).getValue());
        assertEquals("IOCAFunctionsetId", sut.getName());
    }
}
