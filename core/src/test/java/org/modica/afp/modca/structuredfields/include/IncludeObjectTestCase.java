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

package org.modica.afp.modca.structuredfields.include;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
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
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.structuredfields.include.IncludeObject.ObjectType;
import org.modica.afp.modca.structuredfields.types.IncludeType;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link IncludeObject}.
 */
public class IncludeObjectTestCase extends StructuredFieldWithTripletsTestCase<IncludeObject> {

    private IncludeObject hasAllParams;
    private IncludeObject useXOriginArea;
    private IncludeObject useYOriginArea;
    private IncludeObject useXObjectRotation;
    private IncludeObject useYObjectRotation;
    private IncludeObject useXOriginOffset;
    private IncludeObject useYOriginOffset;

    private final String objName = "objsName";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(IncludeType.IOB);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        ByteBuffer bb = ByteBuffer.allocate(27);
        bb.put(objName.getBytes("Cp500"));
        byte[] paramByteArray = ByteUtils.createByteArray(0, 0xBB,
                1, 2, 3,
                4, 5, 6,
                0, 0,
                0x2D, 0,
                7, 8, 9,
                10, 11, 12,
                1);

        bb.put(paramByteArray);
        byte[] constructorArray = bb.array();
        Parameters params = new Parameters(constructorArray);
        hasAllParams = new IncludeObject(intro, triplets, params);
        setMembers(hasAllParams, intro, triplets);

        fillWithFF(constructorArray, 10, 3);
        Parameters useXOA = new Parameters(constructorArray);
        useXOriginArea = new IncludeObject(intro, triplets, useXOA);

        fillWithFF(constructorArray, 13, 3);
        params = new Parameters(constructorArray);
        useYOriginArea = new IncludeObject(intro, triplets, params);

        fillWithFF(constructorArray, 16, 2);
        params = new Parameters(constructorArray);
        useXObjectRotation = new IncludeObject(intro, triplets, params);

        fillWithFF(constructorArray, 18, 2);
        params = new Parameters(constructorArray);
        useYObjectRotation = new IncludeObject(intro, triplets, params);

        fillWithFF(constructorArray, 20, 3);
        params = new Parameters(constructorArray);
        useXOriginOffset = new IncludeObject(intro, triplets, params);

        fillWithFF(constructorArray, 23, 3);
        params = new Parameters(constructorArray);
        useYOriginOffset = new IncludeObject(intro, triplets, params);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(ObjectType.GRAPHICS_GOCA, hasAllParams.getObjType());
        assertEquals(objName, hasAllParams.getObjName());
        assertEquals(0x10203, hasAllParams.getXoaOset());
        assertEquals(false, hasAllParams.useXAxisOriginArea());
        assertEquals(0x40506, hasAllParams.getYoaOset());
        assertEquals(false, hasAllParams.useYAxisOriginArea());
        assertEquals(Rotation.ZERO, hasAllParams.getXoaOrent());
        assertEquals(false, hasAllParams.useXAxisObjectRotation());
        assertEquals(Rotation.NINETY, hasAllParams.getYoaOrent());
        assertEquals(false, hasAllParams.useYAxisObjectRotation());
        assertEquals(0x70809, hasAllParams.getXocaOset());
        assertEquals(false, hasAllParams.useXAxisOriginOffset());
        assertEquals(0xA0B0C, hasAllParams.getYocaOset());
        assertEquals(false, hasAllParams.useYAxisOriginOffset());
        assertEquals(ReferenceCoordinateSystem.ORIGIN, hasAllParams.getRefCSys());

        assertEquals(0xFFFFFF, useXOriginArea.getXoaOset());
        assertEquals(true, useXOriginArea.useXAxisOriginArea());

        assertEquals(0xFFFFFF, useYOriginArea.getYoaOset());
        assertEquals(true, useYOriginArea.useYAxisOriginArea());

        assertEquals(null, useXObjectRotation.getXoaOrent());
        assertEquals(true, useXObjectRotation.useXAxisObjectRotation());

        assertEquals(null, useYObjectRotation.getYoaOrent());
        assertEquals(true, useYObjectRotation.useXAxisObjectRotation());

        assertEquals(0xFFFFFF, useXOriginOffset.getXocaOset());
        assertEquals(true, useXOriginOffset.useXAxisOriginOffset());

        assertEquals(0xFFFFFF, useYOriginOffset.getYocaOset());
        assertEquals(true, useYOriginOffset.useYAxisOriginOffset());
    }

    private void fillWithFF(byte[] array, int index, int length) {
        for (int i = index; i < index + length; i++) {
            array[i] = (byte) 0xFF;
        }
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ObjectName", objName));
        expectedParams.add(new ParameterAsString("ObjectType", ObjectType.GRAPHICS_GOCA));
        expectedParams.add(new ParameterAsString("X-AxisObjectAreaOffset", 0x10203));
        expectedParams.add(new ParameterAsString("Y-AxisObjectAreaOffset", 0x40506));
        expectedParams.add(new ParameterAsString("X-AxisObjectOrientation", Rotation.ZERO));
        expectedParams.add(new ParameterAsString("Y-AxisObjectOrientation", Rotation.NINETY));
        expectedParams.add(new ParameterAsString("X-AxisObjectOffset", 0x70809));
        expectedParams.add(new ParameterAsString("Y-AxisObjectOffset", 0xA0B0C));
        expectedParams.add(new ParameterAsString("ReferenceCoordSystem", ReferenceCoordinateSystem.ORIGIN));
        testParameters(expectedParams, hasAllParams);
    }
}
