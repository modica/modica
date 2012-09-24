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
import org.modica.afp.modca.Parameters;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link ObjectAreaSize}.
 */
public class ObjectAreaSizeTestCase extends TripletTestCase<ObjectAreaSize> {

    private ObjectAreaSize x;

    @Before
    @Override
    public void setUp() {
        byte[] data = ByteUtils.createByteArray(2, 1, 2, 3, 4, 5, 6);
        x = new ObjectAreaSize(new Parameters(data));
        ObjectAreaSize y = new ObjectAreaSize(new Parameters(data));
        ObjectAreaSize z = new ObjectAreaSize(new Parameters(data));

        data[1] = 2;
        ObjectAreaSize notEqual = new ObjectAreaSize(new Parameters(data));

        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x10203, x.getXoaSize());
        assertEquals(0x40506, x.getYoaSize());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("X-AxisSize", x.getXoaSize()));
        expectedParams.add(new ParameterAsString("Y-AxisSize", x.getYoaSize()));
        parameterTester(expectedParams, x);
    }
}
