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

package org.modica.afp.modca;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.structuredfields.descriptor.CodePageDescriptor;
import org.modica.afp.modca.structuredfields.map.MapCodedFont;
import org.modica.afp.modca.structuredfields.map.MapCodedFont.CharacterSetCodePage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.modica.afp.modca.Context.ContextType.FOCA_CPI_REPEATING_GROUP_LENGTH;
import static org.modica.afp.modca.Context.ContextType.MODCA_GCSGID;
import static org.modica.afp.modca.Context.ContextType.MODCA_MAP_CODED_FONT;
import static org.modica.afp.modca.Context.ContextType.PTOCA_SET_CODED_FONT_LOCAL;

public class ContextTestCase {
    private Context sut;
    private final int gcsgid = 121;
    private MapCodedFont mcf;
    private final int focaCPILength = 12;
    private final byte ptocaSetLocalFont = 0x7f;
    private CodePageDescriptor cpDesc;

    @Before
    public void setUp() {
        sut = new ContextImpl();
        mcf = mock(MapCodedFont.class);
        cpDesc = mock(CodePageDescriptor.class);

        sut.put(MODCA_GCSGID, gcsgid);
        sut.put(MODCA_MAP_CODED_FONT, mcf);
        sut.put(FOCA_CPI_REPEATING_GROUP_LENGTH, focaCPILength);
        sut.put(PTOCA_SET_CODED_FONT_LOCAL, ptocaSetLocalFont);
    }

    @Test
    public void testConstructor() {
        Context ctx = new ContextImpl();
        assertEquals(EbcdicStringHandler.DEFAULT_CPGID, ctx.get(MODCA_GCSGID));
    }

    @Test
    public void testGetter() {
        assertEquals(gcsgid, sut.get(MODCA_GCSGID));
        assertEquals(mcf, sut.get(MODCA_MAP_CODED_FONT));
        assertEquals(focaCPILength, sut.get(FOCA_CPI_REPEATING_GROUP_LENGTH));
        assertEquals(ptocaSetLocalFont, sut.get(PTOCA_SET_CODED_FONT_LOCAL));
    }

    @Test
    public void setCpgid() {
        // This takes quite a bit of setup but basically here goes:
        CharacterSetCodePage cscp = mock(CharacterSetCodePage.class);
        // set the CodePageName
        String cpName = "this name is purely for testing";
        when(cscp.getCodePage()).thenReturn(cpName);
        // mcf is mocked, for the PTOCA local font ID we want the charset-codepage couplet
        when(mcf.getFontMappings(ptocaSetLocalFont)).thenReturn(cscp);

        // from the charset-codepage couplet we want a specific cpgid, this can be any number
        int cpgid = 100000100;
        when(cpDesc.getCpgid()).thenReturn(cpgid);

        // here we set the actual codepage name and CodePageDescriptor
        sut.setCurrentCodePageName(cpName);
        sut.setCpgidForCodePage(cpDesc);

        assertEquals(sut.getPTXEncoding(), cpgid);
    }

    @Test(expected = IllegalStateException.class)
    public void setCpgidWithoutCodePageNameFailureCase() {
        sut.setCpgidForCodePage(cpDesc);
    }

    @Test(expected = IllegalStateException.class)
    public void setNullCodePageFailureCase() {
        sut.setCurrentCodePageName("The following statement should throw an exception");
        sut.setCurrentCodePageName("Stop! Hammer time!");
    }

    @Test(expected = IllegalStateException.class)
    public void testEndCodePage() {
        sut.endCodePage();
        sut.setCpgidForCodePage(cpDesc);
    }
}
