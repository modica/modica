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
import org.modica.afp.modca.triplets.ResourceLocalId.ResourceType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link ResourceLocalId}.
 */
public class ResourceLocalIdTestCase extends TripletTestCase<ResourceLocalId> {
    private ResourceLocalId x;

    @Before
    @Override
    public void setUp() {
        byte[] data = new byte[] { 0x00, 0x02, 0x03 };
        x = new ResourceLocalId(new Parameters(data));
        ResourceLocalId y = new ResourceLocalId(new Parameters(data));
        ResourceLocalId z = new ResourceLocalId(new Parameters(data));
        Parameters params = new Parameters(data);
        params.skip(1);
        ResourceLocalId notEqual = new ResourceLocalId(params);

        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        testResourceType((byte) 0x00, ResourceType.USAGE_DEPENDENT);
        testResourceType((byte) 0x02, ResourceType.PAGE_OVERLAY);
        testResourceType((byte) 0x05, ResourceType.CODED_FONT);
        testResourceType((byte) 0x07, ResourceType.COLOR_ATTRIBUTE_TABLE);
    }

    private void testResourceType(byte typeId, ResourceType type) {
        byte[] data = new byte[] { typeId, 0x01 };
        ResourceLocalId testSubject = new ResourceLocalId(new Parameters(data));
        assertEquals(type, testSubject.getResourceType());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ResourceType", x.getResourceType()));
        expectedParams.add(new ParameterAsString("ResourceLocalId",
                ByteUtils.bytesToHex(x.getResourceLocalId())));
        parameterTester(expectedParams, x);
    }
}
