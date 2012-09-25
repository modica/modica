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
import org.modica.afp.modca.common.MapValue;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link MappingOption}.
 */
public class MappingOptionTestCase extends TripletTestCase<MappingOption> {
    private MappingOption x;
    private final MapValue mapValue = MapValue.CenterAndTrim;

    @Before
    @Override
    public void setUp() {
        x = new MappingOption(mapValue.getValue());
        MappingOption y = new MappingOption(mapValue.getValue());
        MappingOption z = new MappingOption(mapValue.getValue());
        MappingOption notEqual = new MappingOption((byte) 0x20);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetters() {
        assertEquals(3, x.getLength());
        assertEquals(mapValue, x.getMapvalue());
        assertEquals(TripletIdentifiers.mapping_option, x.getTid());
    }

    @Test
    public void testMapValues() {
        assertEquals(0x00, MapValue.Position.getValue());
        assertEquals(0x10, MapValue.PositionAndTrim.getValue());
        assertEquals(0x20, MapValue.ScaleToFit.getValue());
        assertEquals(0x30, MapValue.CenterAndTrim.getValue());
        assertEquals(0x41, MapValue.ImagePointToPel.getValue());
        assertEquals(0x42, MapValue.ImagePointToPelWithDoubleDot.getValue());
        assertEquals(0x50, MapValue.ReplicateAndTrim.getValue());
        assertEquals(0x60, MapValue.ScaleToFill.getValue());
        assertEquals(0x70, MapValue.UP3iPrintDataMapping.getValue());

        assertEquals(MapValue.Position, MapValue.getMapValue((byte) 0x00));
        assertEquals(MapValue.PositionAndTrim, MapValue.getMapValue((byte) 0x10));
        assertEquals(MapValue.ScaleToFit, MapValue.getMapValue((byte) 0x20));
        assertEquals(MapValue.CenterAndTrim, MapValue.getMapValue((byte) 0x30));
        assertEquals(MapValue.ImagePointToPel, MapValue.getMapValue((byte) 0x41));
        assertEquals(MapValue.ImagePointToPelWithDoubleDot, MapValue.getMapValue((byte) 0x42));
        assertEquals(MapValue.ReplicateAndTrim, MapValue.getMapValue((byte) 0x50));
        assertEquals(MapValue.ScaleToFill, MapValue.getMapValue((byte) 0x60));
        assertEquals(MapValue.UP3iPrintDataMapping, MapValue.getMapValue((byte) 0x70));
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("MapValue", mapValue));
        parameterTester(expectedParams, x);
    }
}
