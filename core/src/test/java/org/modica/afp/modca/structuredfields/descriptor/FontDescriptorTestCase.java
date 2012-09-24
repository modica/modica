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

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.foca.FontWeightClass;
import org.modica.afp.foca.FontWidthClass;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.structuredfields.types.DescriptorType;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test case for {@link FontDescriptor}.
 */
public class FontDescriptorTestCase extends StructuredFieldWithTripletsTestCase<FontDescriptor> {

    private FontDescriptor sut;
    private final String description = "Typeface descriptor has 32 chars";
    private StructuredFieldIntroducer intro;
    byte[] sutBytes;

    @Before
    public void setUp() throws UnsupportedEncodingException, MalformedURLException {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(DescriptorType.FND);

        ByteBuffer bb = ByteBuffer.allocate(80);
        byte[] descBytes = description.getBytes("Cp500");

        bb.put(descBytes);
        bb.put(ByteUtils.createByteArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                15, 16, 17));
        bb.put(new byte[15]);
        bb.put(ByteUtils.createByteArray(0xD8)); // Font Design flags
        bb.put(new byte[11]);
        bb.put(ByteUtils.createByteArray(1, 2, 3, 4));
        sutBytes = bb.array();

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);
        sut = new FontDescriptor(intro, triplets, new Parameters(sutBytes));

        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(description, sut.getTypefaceDescription());
        assertEquals(FontWeightClass.ULTRALIGHT, sut.getFontWeight());
        assertEquals(FontWidthClass.EXTRACONDENSED, sut.getFontWidth());

        assertEquals(0x304, sut.getMaximumVerticalFontSize());
        assertEquals(0x506, sut.getNominalVerticalFontSize());
        assertEquals(0x708, sut.getMinimumVerticalFontSize());

        assertEquals(0x90A, sut.getMaximumHorizontalFontSize());
        assertEquals(0xB0C, sut.getNominalHorizontalFontSize());
        assertEquals(0xD0E, sut.getMinimumHorizontalFontSize());

        assertEquals(15, sut.getDesignGeneralClass());
        assertEquals(16, sut.getDesignSubClass());
        assertEquals(17, sut.getDesignSpecificClass());

        assertTrue(sut.isItalic());
        assertTrue(sut.hasUnderscore());
        assertTrue(sut.isHollow());
        assertTrue(sut.isOverstruck());

        assertEquals(0x102, sut.getGcsgid());
        assertEquals(0x304, sut.getFgid());
    }

    @Test
    public void testFontDesignFlags() throws UnsupportedEncodingException, MalformedURLException {
        sutBytes[64] = 0;
        sut = createdescriptor();
        assertFalse(sut.isItalic());
        assertFalse(sut.hasUnderscore());
        assertFalse(sut.isHollow());
        assertFalse(sut.isOverstruck());

        sutBytes[64] = (byte) 0x80;
        sut = createdescriptor();
        assertTrue(sut.isItalic());
        assertFalse(sut.hasUnderscore());
        assertFalse(sut.isHollow());
        assertFalse(sut.isOverstruck());

        sutBytes[64] = 0x40;
        sut = createdescriptor();
        assertFalse(sut.isItalic());
        assertTrue(sut.hasUnderscore());
        assertFalse(sut.isHollow());
        assertFalse(sut.isOverstruck());

        sutBytes[64] = 0x10;
        sut = createdescriptor();
        assertFalse(sut.isItalic());
        assertFalse(sut.hasUnderscore());
        assertTrue(sut.isHollow());
        assertFalse(sut.isOverstruck());

        sutBytes[64] = 8;
        sut = createdescriptor();
        assertFalse(sut.isItalic());
        assertFalse(sut.hasUnderscore());
        assertFalse(sut.isHollow());
        assertTrue(sut.isOverstruck());
    }

    private FontDescriptor createdescriptor() throws UnsupportedEncodingException,
            MalformedURLException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(DescriptorType.FND);
        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);
        return new FontDescriptor(intro, triplets, new Parameters(sutBytes));
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("TypefaceDescription", description));
        expectedParams.add(new ParameterAsString("FontWeight", FontWeightClass.ULTRALIGHT));
        expectedParams.add(new ParameterAsString("FontWidth", FontWidthClass.EXTRACONDENSED));
        expectedParams.add(new ParameterAsString("MaxPointSize", 0x304));
        expectedParams.add(new ParameterAsString("NominalPointSize", 0x506));
        expectedParams.add(new ParameterAsString("MinPointSize", 0x708));
        expectedParams.add(new ParameterAsString("MaxHorizontalSize", 0x90A));
        expectedParams.add(new ParameterAsString("NominalHorizontalSize", 0xB0C));
        expectedParams.add(new ParameterAsString("MinHorizontalSize", 0xD0E));
        expectedParams.add(new ParameterAsString("DesignGeneralClass", ByteUtils.bytesToHex((byte) 0x0F)));
        expectedParams.add(new ParameterAsString("DesignSubClass", ByteUtils.bytesToHex((byte) 0x10)));
        expectedParams.add(new ParameterAsString("DesignSpecificClass", ByteUtils.bytesToHex((byte) 0x11)));
        expectedParams.add(new ParameterAsString("isItalic", true));
        expectedParams.add(new ParameterAsString("isHollow", true));
        expectedParams.add(new ParameterAsString("isOverStruck", true));
        expectedParams.add(new ParameterAsString("GCSGID", 0x102));
        expectedParams.add(new ParameterAsString("FGID", 0x304));
        testParameters(expectedParams, sut);
    }
}
