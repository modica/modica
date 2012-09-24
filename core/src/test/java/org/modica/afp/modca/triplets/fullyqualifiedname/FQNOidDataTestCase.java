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

package org.modica.afp.modca.triplets.fullyqualifiedname;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.ParameterAsStringTestCase;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test case for {@link FQNOidData}.
 */
public class FQNOidDataTestCase extends TripletTestCase<FQNOidData> {

    private FQNOidData x;
    private byte[] byteData;
    private int length;
    private ObjectId oid;

    @Before
    @Override
    public void setUp() {
        byteData = ByteUtils.createByteArray(6, 1, 2, 3, 4, 5, 6, 7);
        length = 8;

        oid = new ObjectId(new Parameters(byteData), length);

        x = createTestSubject(byteData, length);
        FQNOidData y = createTestSubject(byteData, length);
        FQNOidData z = createTestSubject(byteData, length);

        byteData[1] = 2;
        FQNOidData notEqual = createTestSubject(byteData, length);
        setXYZ(x, y, z, notEqual);
    }

    private FQNOidData createTestSubject(byte[] data, int length) {
        return new FQNOidData(length, new ObjectId(new Parameters(data), length), FQNType.attribute_gid);
    }

    @Test
    @Override
    public void testGetParameters() {
        assertEquals(FQNType.attribute_gid, x.getFQNType());
        assertEquals(FQNFmt.oid, x.getFormat());
        assertTrue(oid.equals(x.getOid()));

        List<ParameterAsString> expectedParameters = new ArrayList<ParameterAsString>();
        expectedParameters.add(new ParameterAsString(x.getFQNType().name(), oid));
        ParameterAsStringTestCase.testParameters(expectedParameters, x.getParameters());
    }
}
