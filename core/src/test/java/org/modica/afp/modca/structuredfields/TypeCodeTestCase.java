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

package org.modica.afp.modca.structuredfields;

import org.junit.Test;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * A test case for {@link TypeCode}.
 */
public class TypeCodeTestCase {

    @Test
    public void testTypeCodes() {
        testTypeCodeValue(0xA0, TypeCode.Attribute);
        testTypeCodeValue(0xA2, TypeCode.CopyCount);
        testTypeCodeValue(0xA6, TypeCode.Descriptor );
        testTypeCodeValue(0xA7, TypeCode.Control);
        testTypeCodeValue(0xA8, TypeCode.Begin);
        testTypeCodeValue(0xA9, TypeCode.End);
        testTypeCodeValue(0xAB, TypeCode.Map);
        testTypeCodeValue(0xAC, TypeCode.Position);
        testTypeCodeValue(0xAD, TypeCode.Process);
        testTypeCodeValue(0xAF, TypeCode.Include);
        testTypeCodeValue(0xB0, TypeCode.Table);
        testTypeCodeValue(0xB1, TypeCode.Migration);
        testTypeCodeValue(0xB2, TypeCode.Variable);
        testTypeCodeValue(0xB4, TypeCode.Link);
        testTypeCodeValue(0xEE, TypeCode.Data);
    }

    private void testTypeCodeValue(int expected, TypeCode typeCode) {
        assertEquals((byte) expected, typeCode.getValue());

        CategoryCode cc = CategoryCode.active_environment_group;
        byte[] expectedBytes = ByteUtils.createByteArray(0xD3, expected, cc.getValue());
        assertArrayEquals(expectedBytes, typeCode.getIdForType(cc.getValue()));
        assertArrayEquals(expectedBytes, typeCode.getIdForType(cc));
    }
}
