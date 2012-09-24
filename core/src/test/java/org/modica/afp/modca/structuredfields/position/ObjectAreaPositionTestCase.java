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

package org.modica.afp.modca.structuredfields.position;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.ReferenceCoordinateSystem;
import org.modica.afp.modca.common.Rotation;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.types.PositionType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link ObjectAreaPosition}.
 */
public class ObjectAreaPositionTestCase extends StructuredFieldTestCase<ObjectAreaPosition> {

    private ObjectAreaPosition sut;

    @Before
    public void setUp() {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(PositionType.OBP);
        byte[] data = ByteUtils.createByteArray(
                1, // oaPosId
                23, //Length must be 23
                2, 3, 4, // xoaOset
                5, 6, 7, // yoaOset
                0, 0, // xoaOrent
                0x2D, 0, // yoaOrent
                0,
                1, 2, 3, //xocaOset
                4, 5, 6, // yocaOset
                0, 0, // xocaOrent must be 0
                0x2D, 0, //yocaOrent
                1);// refCSys
        Parameters params = new Parameters(data);
        sut = new ObjectAreaPosition(intro, params);

        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(1, sut.getOaPosId());
        assertEquals(0x20304, sut.getXoaOset());
        assertEquals(0x50607, sut.getYoaOset());
        assertEquals(Rotation.ZERO, sut.getXoaOrent());
        assertEquals(Rotation.NINETY, sut.getYoaOrent());
        assertEquals(0x10203, sut.getXocaOset());
        assertEquals(0x40506, sut.getYocaOset());
        assertEquals(Rotation.ZERO, sut.getXocaOrent());
        assertEquals(Rotation.NINETY, sut.getYocaOrent());
        assertEquals(ReferenceCoordinateSystem.ORIGIN, sut.getRefCSys());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ObjectAreaPositionId", ByteUtils.bytesToHex((byte) 0x01)));
        expectedParams.add(new ParameterAsString("X-AxisObjectAreaOffset", 0x20304));
        expectedParams.add(new ParameterAsString("Y-AxisObjectAreaOffset", 0x50607));
        expectedParams.add(new ParameterAsString("X-AxisObjectOrientation", Rotation.ZERO));
        expectedParams.add(new ParameterAsString("Y-AxisObjectOrientation", Rotation.NINETY));
        expectedParams.add(new ParameterAsString("X-AxisObjectOffset", 0x10203));
        expectedParams.add(new ParameterAsString("Y-AxisObjectOffset", 0x40506));
        expectedParams.add(new ParameterAsString("ReferenceCoordSystem", ReferenceCoordinateSystem.ORIGIN));
        testParameters(expectedParams, sut);
    }
}
