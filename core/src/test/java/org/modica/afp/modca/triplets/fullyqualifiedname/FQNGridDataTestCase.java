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
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

public class FQNGridDataTestCase extends TripletTestCase<FQNGridData> {
    private FQNGridData x;
    private byte[] gridBytes;
    private FQNType type;
    private int length;

    @Before
    @Override
    public void setUp() {
        gridBytes = ByteUtils.createByteArray(00, 01, 00, 02, 00, 03, 00, 04);
        type = FQNType.font_family_name;
        length = 0;

        x = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes)), type);
        FQNGridData y = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes)), type);
        FQNGridData z = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes)), type);

        byte[] notEqualsGrid = new byte[gridBytes.length];
        System.arraycopy(gridBytes, 0, notEqualsGrid, 0, gridBytes.length);
        notEqualsGrid[7] = 0x05;

        FQNGridData notEqual = new FQNGridData(length, new GlobalResourceId(new Parameters(notEqualsGrid)), type);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetters() {
        GlobalResourceId expected = new GlobalResourceId(new Parameters(gridBytes));
        assertEquals(expected, x.getGrid());
        assertEquals(FQNFmt.character_string, x.getFormat());
        assertEquals(type, x.getFQNType());
        assertEquals(length, x.getLength());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("GlobalResourceId", x.getGrid()));
        parameterTester(expectedParams, x);
    }
}
