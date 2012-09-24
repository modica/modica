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
import org.modica.afp.modca.triplets.foca.ResourceManagement.CRCResourceManagement;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CRCResourceManagementTestCase extends TripletTestCase<ResourceManagement> {

    private ResourceManagement x;
    private ResourceManagement notEqual;

    @Before
    @Override
    public void setUp() {
        byte[] crcBytes = ByteUtils.createByteArray(1, 2, 3, 1);
        x = ResourceManagement.parse(new Parameters(crcBytes));
        ResourceManagement y = ResourceManagement.parse(new Parameters(crcBytes));
        ResourceManagement z = ResourceManagement.parse(new Parameters(crcBytes));

        crcBytes[3] = 0;
        notEqual = ResourceManagement.parse(new Parameters(crcBytes));
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x203, x.getResourceManagementValue());
        assertTrue(x instanceof CRCResourceManagement);
        assertTrue(((CRCResourceManagement) x).isPublic());

        assertFalse(((CRCResourceManagement) notEqual).isPublic());
    }

    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ResourceManagementValue", String.valueOf(0x203)));
        expectedParams.add(new ParameterAsString("isPublic", String.valueOf(true)));
        parameterTester(expectedParams, x);
    }

}
