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

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test case for {@link ObjectId}.
 */
public class ObjectIdTestCase extends TripletTestCase<ObjectId> {

    private ObjectId x;
    private ObjectId notEqual;
    private byte[] byteData;

    @Before
    @Override
    public void setUp() {
        byteData = ByteUtils.createByteArray(6, 2, 3, 4, 5, 6, 7, 8, 9);
        x = new ObjectId(new Parameters(byteData), byteData.length);
        ObjectId y = new ObjectId(new Parameters(byteData), byteData.length);
        ObjectId z = new ObjectId(new Parameters(byteData), byteData.length);

        byteData[1] = 1;
        notEqual = new ObjectId(new Parameters(byteData), byteData.length);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    @Override
    public void testGetParameters() {
        byte[] expected = new byte[byteData.length - 1];
        System.arraycopy(byteData, 1, expected, 0, byteData.length - 1);
        assertArrayEquals(expected, notEqual.getOid());
    }
}
