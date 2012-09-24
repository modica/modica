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

package org.modica.afp.modca.triplets.fullyqualifiedname;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test case for {@link FullyQualifiedName}.
 */
public class FullyQualifiedNameTestCase {

    public static final String FONT_CHAR_SET_NAME_REF = "0C028600C3F0C8F2F0F0D1F0";
    public static final String CODE_PAGE_NAME_REF = "0C028500E3F1E5F1F0F5F0F0";

    @Test
    public void testFontCharSetNameRef() throws UnsupportedEncodingException, MalformedURLException {
        int length = 0x0C;
        testFQNWithCharData("C0H200J0", length, FQNType.font_charset_name_ref,
                FONT_CHAR_SET_NAME_REF);
    }

    @Test
    public void testCodePageNameRef() throws UnsupportedEncodingException, MalformedURLException {
        int length = 0x0C;
        testFQNWithCharData("T1V10500", length, FQNType.code_page_name_ref, CODE_PAGE_NAME_REF);
    }

    @Test
    public void testOidFQN() throws MalformedURLException, UnsupportedEncodingException {
        byte[] oidData = ByteUtils.createByteArray(0x0C, 2, 1, 0x10, 6, 1, 2, 3, 4, 5, 6, 7, 8);
        FullyQualifiedName fqn = createFQN(oidData);
        assertTrue(fqn instanceof FQNOidData);
    }

    @Test
    public void testURLDataFQN() throws UnsupportedEncodingException, MalformedURLException {
        byte[] strData = "http://www.thisisatest.com".getBytes("Cp500");
        byte[] bytes = new byte[strData.length + 4];
        System.arraycopy(strData, 0, bytes, 4, strData.length);
        bytes[0] = 0x0C;
        bytes[1] = (byte) bytes.length;
        bytes[2] = 1;
        bytes[3] = 0x20;
        FullyQualifiedName fqn = createFQN(bytes);
        assertTrue(fqn instanceof FQNUrlData);
    }

    private void testFQNWithCharData(String expectedStr, int length, FQNType type, String hexData)
            throws UnsupportedEncodingException, MalformedURLException {
        FullyQualifiedName fqn = createFQN(ByteUtils.hexToBytes(hexData));
        assertEquals(length, fqn.getLength());
        assertEquals(type, fqn.getFQNType());
        FQNCharStringData fcsn = (FQNCharStringData) fqn;
        assertEquals(expectedStr, fcsn.getString());
    }

    public static FullyQualifiedName createFQN(byte[] bytes) throws MalformedURLException,
    UnsupportedEncodingException {
        Parameters params = new Parameters(bytes);
        params.skip(2);
        return FullyQualifiedName.parse(params, bytes.length);
    }
}
