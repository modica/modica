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

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link DescriptorPosition}.
 */
public class DescriptorPositionTestCase extends TripletTestCase<DescriptorPosition> {

    private DescriptorPosition x;
    private final static byte testByte = 0x04;

    @Before
    @Override
    public void setUp() {
        x = new DescriptorPosition(testByte);
        DescriptorPosition y = new DescriptorPosition(testByte);
        DescriptorPosition z = new DescriptorPosition(testByte);
        DescriptorPosition notEqual = new DescriptorPosition((byte) 0x05);

        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(testByte, x.getDesPosId());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ObjectAreaPositionId",
                ByteUtils.bytesToHex((byte) 0x04)));
        parameterTester(expectedParams, x);
    }
}
