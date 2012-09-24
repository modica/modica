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

package org.modica.afp.modca.structuredfields.descriptor;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.ioca.IocaFunctionSetId;
import org.modica.afp.ioca.SelfDefiningField;
import org.modica.afp.ioca.SetBilevelImageColor;
import org.modica.afp.ioca.SetExtendedBilevelImageColor;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.PresentationSpaceUnits;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.types.DescriptorType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test case for {@link ImageDataDescriptor}.
 */
public class ImageDataDescriptorTestCase extends StructuredFieldTestCase<ImageDataDescriptor> {

    private ImageDataDescriptor oneSelfDefiningField;
    private ImageDataDescriptor severalSelfDefiningFields;

    @Before
    public void setUp() {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(DescriptorType.IID);
        byte[] bytes = ByteUtils.createByteArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 0xF7, 2, 1, 0x0A);
        Parameters params = new Parameters(bytes);
        oneSelfDefiningField = new ImageDataDescriptor(intro, params);

        ByteBuffer bb = ByteBuffer.allocate(32);
        byte[] setBilevelImageColor = ByteUtils.createByteArray(0xF6, 4, 0, 0, 0, 1);
        byte[] setExtendedBilevelImageColor =
                ByteUtils.createByteArray(0xF4, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

        bb.put(bytes);
        bb.put(setExtendedBilevelImageColor);
        bb.put(setBilevelImageColor);
        Parameters newParams = new Parameters(bb.array());
        severalSelfDefiningFields = new ImageDataDescriptor(intro, newParams);
        setMembers(severalSelfDefiningFields, intro);

        try {
            bytes[9] = (byte) 0xFF;
            params = new Parameters(bytes);
            new ImageDataDescriptor(intro, params);
            fail("Failed to throw exception");
        } catch (IllegalArgumentException iae) {
            // Pass
        }
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, oneSelfDefiningField.getXResol());
        assertEquals(0x304, oneSelfDefiningField.getYResol());
        assertEquals(0x506, oneSelfDefiningField.getXSize());
        assertEquals(0x708, oneSelfDefiningField.getYSize());
        assertEquals(PresentationSpaceUnits.INCHES_10, oneSelfDefiningField.getUnitsBase());
        List<SelfDefiningField> oneSdf = oneSelfDefiningField.getSelfDefiningFields();
        assertEquals(1, oneSdf.size());
        assertTrue(oneSdf.get(0) instanceof IocaFunctionSetId);

        List<SelfDefiningField> severalSdfs = severalSelfDefiningFields.getSelfDefiningFields();
        assertEquals(3, severalSdfs.size());
        assertTrue(severalSdfs.get(0) instanceof IocaFunctionSetId);
        assertTrue(severalSdfs.get(1) instanceof SetExtendedBilevelImageColor);
        assertTrue(severalSdfs.get(2) instanceof SetBilevelImageColor);
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("XResolution", 0x102));
        expectedParams.add(new ParameterAsString("YResolution", 0x304));
        expectedParams.add(new ParameterAsString("XSize", 0x506));
        expectedParams.add(new ParameterAsString("YSize", 0x708));
        expectedParams.add(new ParameterAsString("UnitBase", "INCHES_10"));
        testParameters(expectedParams, oneSelfDefiningField);
    }
}
