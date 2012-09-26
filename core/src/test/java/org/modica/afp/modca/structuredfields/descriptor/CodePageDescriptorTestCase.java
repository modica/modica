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
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.EncodingScheme;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.types.DescriptorType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test case for {@link CodePageDescriptor}.
 */
public class CodePageDescriptorTestCase extends StructuredFieldTestCase<CodePageDescriptor> {

    private CodePageDescriptor sut;
    private CodePageDescriptor sutNoScheme;
    private final String cpDesc = "This string has to be 32 charss.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(DescriptorType.CPD);

        ByteBuffer bb = ByteBuffer.allocate(44);
        bb.put(cpDesc.getBytes("Cp500"));
        byte[] paramsBytes = ByteUtils.createByteArray(0, 8, 1, 2, 3, 4, 5, 6, 7, 8, 0x62, 0);
        bb.put(paramsBytes);
        Parameters params = new Parameters(bb.array());
        Context ctx = new ContextImpl();
        ctx.setCurrentCodePageName("test");
        sut = new CodePageDescriptor(intro, params, ctx);

        setMembers(sut, intro);

        byte[] noSchemeBytes = ByteUtils.createByteArray(0, 8, 1, 2, 3, 4, 5, 6, 7, 8);
        bb = ByteBuffer.allocate(42);
        bb.put(cpDesc.getBytes("Cp500"));
        bb.put(noSchemeBytes);
        ctx = new ContextImpl();
        ctx.setCurrentCodePageName("test");
        sutNoScheme = new CodePageDescriptor(intro, new Parameters(bb.array()), ctx);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(cpDesc, sut.getCodePageDescriptor());
        assertEquals(0x1020304, sut.getNumCdPts());
        assertEquals(0x506, sut.getGcsgid());
        assertEquals(0x708, sut.getCpgid());
        assertEquals(EncodingScheme.DOUBLE_BYTE_EBCDIC, sut.getEncodingScheme());
        assertNull(sutNoScheme.getEncodingScheme());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("Description", cpDesc));
        expectedParams.add(new ParameterAsString("NumberOfCodePoints", 16909060));
        expectedParams.add(new ParameterAsString("GCSGID", 1286));
        expectedParams.add(new ParameterAsString("CPGID", 1800));
        expectedParams.add(new ParameterAsString("EncodingScheme",
                EncodingScheme.DOUBLE_BYTE_EBCDIC));
        testParameters(expectedParams, sut);
    }
}
