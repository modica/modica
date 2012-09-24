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

package org.modica.afp.modca.triplets.foca;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link ResourceManagement}.
 */
public class FontResourceManagementTestCase extends TripletTestCase<ResourceManagement> {

    private ResourceManagement x;
    private ResourceManagement notEqual;

    @Before
    @Override
    public void setUp() {
        byte[] data = ByteUtils.createByteArray(2,
                3, 4, 5, 6, // rmValue
                0xF3, // year1
                0xF1, 0xF2, // year2
                0xF3, 0xF4, 0xF5, // day
                0xF3, 0xF4, //hour
                0xF5, 0xF6, //minute
                0xF1, 0xF2, //second
                0xF9, 0xF9); // second / 10
        x = ResourceManagement.parse(new Parameters(data));
        ResourceManagement y = ResourceManagement.parse(new Parameters(data));
        ResourceManagement z = ResourceManagement.parse(new Parameters(data));

        data[5] = 0x40;

        notEqual = ResourceManagement.parse(new Parameters(data));
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x3040506, x.getResourceManagementValue());
        assertEquals(6, x.getLength());
    }
    
    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();

        String expectedString = "year=" + 2312 + " day=" + 345 + " hour=" + 34
                + " minute=" + 56;
        expectedParams.add(new ParameterAsString("Date", expectedString));
        parameterTester(expectedParams, x);
    }
}
