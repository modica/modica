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

package org.modica.afp.modca.structuredfields.control;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.CPIRepeatingGroupLength;
import org.modica.afp.modca.structuredfields.ControlType;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.modica.afp.modca.Context.ContextType.FOCA_CPI_REPEATING_GROUP_LENGTH;
import static org.modica.afp.modca.common.CPIRepeatingGroupLength.DOUBLE_BYTE;
import static org.modica.afp.modca.common.CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE;
import static org.modica.afp.modca.common.CPIRepeatingGroupLength.SINGLE_BYTE;
import static org.modica.afp.modca.common.CPIRepeatingGroupLength.SINGLE_BYTE_INC_UNICODE;

/**
 * Test case for {@link CodePageControl}.
 */
public class CodePageControlTestCase extends StructuredFieldTestCase<CodePageControl> {

    private final String defaultChar = "TestChar";
    private CodePageControl sut;
    private Context sutCtx;
    private CodePageControl notInvalid;
    private CodePageControl noPresentation;
    private CodePageControl noIncrement;
    private CodePageControl doubleByte;
    private Context doubleByteCtx;
    private CodePageControl singleByteUnicode;
    private Context singleByteUnicodeCtx;
    private CodePageControl doubleByteUnicode;
    private Context doubleByteUnicodeCtx;
    private CodePageControl enableVariableSpace;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(ControlType.CPC);

        ByteBuffer bb = ByteBuffer.allocate(15);

        bb.put(defaultChar.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(0x80, 0x0A, 2, 3, 0x90));
        byte[] data = bb.array();
        sutCtx = new ContextImpl();
        sut = new CodePageControl(intro, new Parameters(data), sutCtx);
        setMembers(sut, intro);

        data[8] = (byte) 0x00;
        notInvalid = new CodePageControl(intro, new Parameters(data), new ContextImpl());

        data[8] = (byte) 0x40;
        noPresentation = new CodePageControl(intro, new Parameters(data), new ContextImpl());

        data[8] = 0x60;
        noIncrement = new CodePageControl(intro, new Parameters(data), new ContextImpl());

        doubleByteCtx = new ContextImpl();
        data[9] = 0x0B;
        doubleByte = new CodePageControl(intro, new Parameters(data), doubleByteCtx);

        singleByteUnicodeCtx = new ContextImpl();
        data[9] = (byte) 0xFE;
        singleByteUnicode = new CodePageControl(intro, new Parameters(data), singleByteUnicodeCtx);

        doubleByteUnicodeCtx = new ContextImpl();
        data[9] = (byte) 0xFF;
        doubleByteUnicode = new CodePageControl(intro, new Parameters(data), doubleByteUnicodeCtx);

        data[12] = (byte) 0x08;
        enableVariableSpace = new CodePageControl(intro, new Parameters(data), new ContextImpl());
    }

    @Test
    public void testGetterMethods() {
        assertEquals(defaultChar, sut.getDefaultGCGID());
        assertTrue(sut.isInvalidCodedCharacter());
        assertFalse(sut.isNoPresensentation());
        assertFalse(sut.isNoIncrement());
        assertEquals(SINGLE_BYTE, sut.getCPIRepeatingGroupLength());
        assertEquals(SINGLE_BYTE, sutCtx.get(FOCA_CPI_REPEATING_GROUP_LENGTH));
        assertEquals(0x02, sut.getSpaceCharacterSectionNumber());
        assertEquals(0x03, sut.getSpaceCharacterCodePoint());
        assertTrue(sut.isAscendingCodePoint());
        assertFalse(sut.isVariableSpaceEnabled());

        assertFalse(notInvalid.isInvalidCodedCharacter());

        assertTrue(noPresentation.isNoPresensentation());

        assertTrue(noIncrement.isNoIncrement());

        assertEquals(DOUBLE_BYTE, doubleByte.getCPIRepeatingGroupLength());
        assertEquals(DOUBLE_BYTE, doubleByteCtx.get(FOCA_CPI_REPEATING_GROUP_LENGTH));

        assertEquals(SINGLE_BYTE_INC_UNICODE, singleByteUnicode.getCPIRepeatingGroupLength());
        assertEquals(SINGLE_BYTE_INC_UNICODE,
                singleByteUnicodeCtx.get(FOCA_CPI_REPEATING_GROUP_LENGTH));

        assertEquals(DOUBLE_BYTE_INC_UNICODE, doubleByteUnicode.getCPIRepeatingGroupLength());
        assertEquals(DOUBLE_BYTE_INC_UNICODE,
                doubleByteUnicodeCtx.get(FOCA_CPI_REPEATING_GROUP_LENGTH));

        assertTrue(enableVariableSpace.isVariableSpaceEnabled());

        assertEquals(0, singleByteUnicode.getDefaultUnicodeIndex());
        assertEquals(0, doubleByteUnicode.getDefaultUnicodeIndex());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("DefaultGCGID", defaultChar));
        expectedParams.add(new ParameterAsString("isInvalidCodedCharacter", true));
        expectedParams.add(new ParameterAsString("isNoPresentation", false));
        expectedParams.add(new ParameterAsString("isNoIncrement", false));
        expectedParams.add(new ParameterAsString("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.SINGLE_BYTE));
        expectedParams.add(new ParameterAsString("SpaceCharSectionNumber", 2));
        expectedParams.add(new ParameterAsString("SpaceCharCodePoint", 3));
        expectedParams.add(new ParameterAsString("isAscendingCodePoint", true));
        expectedParams.add(new ParameterAsString("isVariableSpaceEnabled", false));
        expectedParams.add(new ParameterAsString("DefaultUnicodeValue", 0));
        testParameters(expectedParams, sut);

        expectedParams.set(1, new ParameterAsString("isInvalidCodedCharacter", false));
        testParameters(expectedParams, notInvalid);

        expectedParams.set(2, new ParameterAsString("isNoPresentation", true));
        testParameters(expectedParams, noPresentation);

        expectedParams.set(3, new ParameterAsString("isNoIncrement", true));
        testParameters(expectedParams, noIncrement);

        expectedParams.set(4, new ParameterAsString("CPIRepeatingGroupLength", DOUBLE_BYTE));
        testParameters(expectedParams, doubleByte);

        expectedParams.set(4,
                new ParameterAsString("CPIRepeatingGroupLength", SINGLE_BYTE_INC_UNICODE));
        testParameters(expectedParams, singleByteUnicode);

        expectedParams.set(4,
                new ParameterAsString("CPIRepeatingGroupLength", DOUBLE_BYTE_INC_UNICODE));
        testParameters(expectedParams, doubleByteUnicode);
    }
}
