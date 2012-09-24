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

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.structuredfields.types.AttributeType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class StructuredFieldIntroducerTestCase {
    private StructuredFieldIntroducer sut;
    private StructuredFieldType sutType;

    @Before
    public void setUp() {
        byte[] typeId = ByteUtils.createByteArray(0xD3, 0xAE, 0x89);
        sutType = StructuredFieldTypeFactory.getValue(typeId);
        sut = new StructuredFieldIntroducer(1L, 2, typeId, (byte) 3, 5);
    }

    @Test
    public void testGetters() {
        assertEquals(1L, sut.getOffset());
        assertEquals(2, sut.getLength());
        assertEquals(sutType, sut.getType());
        assertTrue(sut.hasExtData());
        assertEquals(5, sut.getExtLength());
        assertEquals(15, sut.getDataOffset());
        assertEquals(sut.getLength() + sut.getExtLength(), sut.bytesToNextStructuredField());

        assertTrue(StructuredFieldIntroducer.hasSfiExtension((byte) 0x01));
        assertFalse(StructuredFieldIntroducer.hasSfiExtension((byte) 0x02));
    }

    @Test
    public void testHashCodeEquals() {
        // Consistency
        for (int i = 0; i < 100; i++) {
            StructuredFieldIntroducer x = createGenericIntroducer();
            StructuredFieldIntroducer y = createGenericIntroducer();
            StructuredFieldIntroducer z = createGenericIntroducer();

            // Reflectivity
            assertTrue(x.equals(x));
            assertTrue(y.equals(y));
            assertTrue(z.equals(z));

            // Symmetry
            assertTrue(x.equals(y));
            assertTrue(y.equals(z));

            // Transitivity
            assertTrue(x.equals(y));
            assertTrue(y.equals(z));
            assertTrue(x.equals(z));

            // hashCode
            assertEquals(x.hashCode(), y.hashCode());
            assertEquals(y.hashCode(), z.hashCode());
        }
    }

    @Test
    public void testEqualsHashCodeFailureCases() {
        StructuredFieldIntroducer subject = createGenericIntroducer();
        StructuredFieldIntroducer offsetNotEqual = new StructuredFieldIntroducer(2L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x88), (byte) 3, 5);
        checkNotEquals(subject, offsetNotEqual);

        StructuredFieldIntroducer lengthNotEquals = new StructuredFieldIntroducer(1L, 1, ByteUtils.createByteArray(
                0xD3, 0xA0, 0x88), (byte) 3, 5);
        checkNotEquals(subject, lengthNotEquals);

        StructuredFieldIntroducer typeNotEquals = new StructuredFieldIntroducer(1L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x90), (byte) 3, 5);
        checkNotEquals(subject, typeNotEquals);

        StructuredFieldIntroducer flagsNotEquals = new StructuredFieldIntroducer(1L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x88), (byte) 4, 5);
        checkNotEquals(subject, flagsNotEquals);

        StructuredFieldIntroducer extLengthNotEquals = new StructuredFieldIntroducer(1L, 2, ByteUtils.createByteArray(
                0xD3, 0xA0, 0x88), (byte) 3, 6);
        checkNotEquals(subject, extLengthNotEquals);
    }

    private void checkNotEquals(StructuredFieldIntroducer o1, StructuredFieldIntroducer o2) {
        assertFalse(o1.equals(o2));
        assertNotSame(o1.hashCode(), o2.hashCode());
    }

    /**
     * Creates an introducer object, populated with arbitrary values for testing against.
     *
     * @return the introducer
     */
    public static StructuredFieldIntroducer createGenericIntroducer() {
        return createGenericIntroducer(AttributeType.MFC);
    }

    /**
     * Creates an introducer object of SfType type, populated with arbitrary values for testing.
     *
     * @param type the SfType
     * @return the introducer
     */
    public static StructuredFieldIntroducer createGenericIntroducer(StructuredFieldType type) {
        byte[] bytes = ByteUtils.createByteArray(
                0xD3,
                type.getTypeCode().getValue() & 0xFF,
                type.getCategoryCode().getValue() & 0xFF);
        return new StructuredFieldIntroducer(1L, 2, bytes, (byte) 3, 5);
    }

    @Test
    public void testFlags() {
        for (int i = 0; i < 8; i++) {
            boolean hasExtData = (i & 1) > 0;
            boolean hasSegData = (i & 4) > 0;
            boolean hasDataPadding = (i & 16) > 0;
            testSFWithFlag(i, hasExtData, hasSegData, hasDataPadding);
        }
    }

    private void testSFWithFlag(int flag, boolean hasExtData, boolean hasSegData,
            boolean hasDataPadding) {
        byte[] id = ByteUtils.createByteArray(0xD3, 0xA0, 0x88);
        StructuredFieldIntroducer field = new StructuredFieldIntroducer(0, 1, id, (byte) flag, 0);
        assertEquals(hasExtData, field.hasExtData());
        assertEquals(hasSegData, field.hasSegmentedData());
        assertEquals(hasDataPadding, field.hasDataPadding());
    }
}
