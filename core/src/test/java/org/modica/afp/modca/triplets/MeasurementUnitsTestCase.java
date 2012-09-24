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
import org.modica.afp.modca.common.PresentationSpaceUnits;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link MeasurementUnits}.
 */
public class MeasurementUnitsTestCase extends TripletTestCase<MeasurementUnits> {

    private MeasurementUnits x;
    private MeasurementUnits notEqual;

    @Before
    @Override
    public void setUp() {
        byte[] data = ByteUtils.createByteArray(0, 0, 1, 2, 3, 4);
        x = new MeasurementUnits(new Parameters(data));
        MeasurementUnits y = new MeasurementUnits(new Parameters(data));
        MeasurementUnits z = new MeasurementUnits(new Parameters(data));

        data[0] = 1;
        data[1] = 1;
        notEqual = new MeasurementUnits(new Parameters(data));
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(PresentationSpaceUnits.INCHES_10, x.getXoaBase());
        assertEquals(PresentationSpaceUnits.INCHES_10, x.getYoaBase());
        assertEquals(0x0102, x.getXoaUnit());
        assertEquals(0x0304, x.getYoaUnit());

        assertEquals(PresentationSpaceUnits.CENTIMETRE_10, notEqual.getXoaBase());
        assertEquals(PresentationSpaceUnits.CENTIMETRE_10, notEqual.getYoaBase());
        assertEquals(0x0102, notEqual.getXoaUnit());
        assertEquals(0x0304, notEqual.getYoaUnit());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("X-AxisBase", x.getXoaBase()));
        expectedParams.add(new ParameterAsString("Y-AxisBase", x.getYoaBase()));
        expectedParams.add(new ParameterAsString("X-AxisSize", x.getXoaUnit()));
        expectedParams.add(new ParameterAsString("Y-AxisSize", x.getYoaUnit()));
        parameterTester(expectedParams, x);
    }
}
