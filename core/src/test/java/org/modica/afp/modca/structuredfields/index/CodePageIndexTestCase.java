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

package org.modica.afp.modca.structuredfields.index;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.CPIRepeatingGroupLength;
import org.modica.afp.modca.common.GraphicalCharacterUseFlags;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.index.CodePageIndex.CPI;
import org.modica.afp.modca.structuredfields.types.IndexType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link CodePageIndex}.
 */
public class CodePageIndexTestCase extends StructuredFieldTestCase<CodePageIndex> {

    private CodePageIndex sut;

    private CodePageIndex doubleByteUnicodeSut;
    private final String char1Name = "TestChar";
    private final String char2Name = "TestCTwo";
    private final String char3Name = "TestTree";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(IndexType.CPI);

        Context context = new ContextImpl();
        context.put(ContextType.FOCA_CPI_REPEATING_GROUP_LENGTH, CPIRepeatingGroupLength.SINGLE_BYTE);
        sut = createSingleByteCPI(CPIRepeatingGroupLength.SINGLE_BYTE);
        doubleByteUnicodeSut = createDoubleByteCPI(CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE);

        setMembers(sut, intro);
    }

    private CodePageIndex createSingleByteCPI(CPIRepeatingGroupLength cpiRLen)
            throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(IndexType.CPI);
        ByteBuffer bb = ByteBuffer.allocate(30);
        bb.put(char1Name.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(1, 4));
        bb.put(char2Name.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(2, 5));
        bb.put(char3Name.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(4, 6));
        Context context = new ContextImpl();
        context.put(ContextType.FOCA_CPI_REPEATING_GROUP_LENGTH, cpiRLen);
        return new CodePageIndex(intro, new Parameters(bb.array()), context);
    }

    private CodePageIndex createDoubleByteCPI(CPIRepeatingGroupLength cpiRLen)
            throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(IndexType.CPI);
        ByteBuffer bb = ByteBuffer.allocate(42);
        bb.put(char1Name.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(1, 2, 3, 1, 5));
        bb.put(char2Name.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(2, 3, 4, 2, 6, 7));
        bb.put(char3Name.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(4, 1, 2, 3, 1, 2, 3));
        Context context = new ContextImpl();
        context.put(ContextType.FOCA_CPI_REPEATING_GROUP_LENGTH, cpiRLen);
        return new CodePageIndex(intro, new Parameters(bb.array()), context);
    }

    @Test
    public void testGetterMethods() {
        List<CPI> singleByteCPIs = sut.getCodePageIndices();
        testCPI(singleByteCPIs.get(0), char1Name, 4, 0, (byte) 1);
        testCPI(singleByteCPIs.get(1), char2Name, 5, 0, (byte) 2);
        testCPI(singleByteCPIs.get(2), char3Name, 6, 0, (byte) 4);

        List<CPI> doubleByteCPIs = doubleByteUnicodeSut.getCodePageIndices();
        testCPI(doubleByteCPIs.get(0), char1Name, 0x203, 5, (byte) 1);
        testCPI(doubleByteCPIs.get(1), char2Name, 0x304, 0x607, (byte) 2);
        testCPI(doubleByteCPIs.get(2), char3Name, 0x102, 0x10203, (byte) 4);
    }

    private void testCPI(CPI cpi, String gcgid, int codePoint, int unicode, byte flag) {
        assertEquals(gcgid, cpi.getGcgid());
        assertEquals(codePoint, cpi.getCodePoint());
        assertEquals(unicode, cpi.getUnicodeIndex());

        boolean isInvalid = GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag);
        boolean isNoPres = GraphicalCharacterUseFlags.isNoPresentation(flag);
        boolean isNoIncr = GraphicalCharacterUseFlags.isNoIncrement(flag);

        assertEquals(isInvalid, cpi.isInvalidCodedCharacter());
        assertEquals(isNoPres, cpi.isNoPresentation());
        assertEquals(isNoIncr, cpi.isNoIncrement());

        Map<String, String> strings = cpi.getCPIStrings();
        assertEquals(String.valueOf(isInvalid), strings.get("InvalidCodedCharacter"));
        assertEquals(String.valueOf(isNoPres), strings.get("NoPresentation"));
        assertEquals(String.valueOf(isNoIncr), strings.get("NoIncrement"));
        assertEquals(String.valueOf(codePoint), strings.get("CodePoint"));
        assertEquals(String.valueOf(unicode), strings.get("UnicodeIndex"));
        assertEquals(gcgid, strings.get("GCGID"));
    }

    @Test
    @Override
    public void testGetParameters() {
        assertEquals(0, sut.getParameters().size());
    }
}
