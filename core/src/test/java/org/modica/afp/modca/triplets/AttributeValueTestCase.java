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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;

import static org.junit.Assert.assertEquals;

public class AttributeValueTestCase extends TripletTestCase<AttributeValue> {

    private AttributeValue x;

    @Override
    @Before
    public void setUp() throws UnsupportedEncodingException {
        x = createAttributeValue("Hello World!");
        AttributeValue y = createAttributeValue("Hello World!");
        AttributeValue z = createAttributeValue("Hello World!");
        AttributeValue notEqual = createAttributeValue("Not Equal");
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetter() {
        assertEquals("Hello World!", x.getAttributeValue());
    }

    @Override
    @Test
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("AttributeValue", "Hello World!"));
        parameterTester(expectedParams, x);
    }

    public static AttributeValue createAttributeValue(String string)
            throws UnsupportedEncodingException {
        byte[] stringBytes = string.getBytes("Cp500");
        int length = stringBytes.length + 4;
        byte[] data = new byte[length];
        System.arraycopy(stringBytes, 0, data, 4, stringBytes.length);
        data[0] = (byte) length;
        data[1] = 0x36;
        Parameters params = new Parameters(data);
        params.skip(2);
        return new AttributeValue(length, TripletIdentifiers.attribute_value, params);
    }
}
