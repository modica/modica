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

package org.modica.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ByteUtilsTestCase {

    private final ByteUtils littleEndianSut = ByteUtils.getLittleEndianUtils();
    private final ByteUtils bigEndianSut = ByteUtils.getBigEndianUtils();

    private byte[] oneToSeven;

    @Before
    public void setUp() {
        oneToSeven = ByteUtils.createByteArray(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    public void testLittleEndianBytesToUnsignedInt() {
        byte[] bytes = ByteUtils.createByteArray(0x01);
        assertEquals(0x01, littleEndianSut.bytesToUnsignedInt(bytes, 0, 1));
        assertEquals(0x01, littleEndianSut.bytesToUnsignedInt(bytes));

        bytes = ByteUtils.createByteArray(0x01, 0x02);
        assertEquals(0x201, littleEndianSut.bytesToUnsignedInt(bytes, 0, 2));
        assertEquals(0x201, littleEndianSut.bytesToUnsignedInt(bytes));

        bytes = ByteUtils.createByteArray(0x01, 0x02, 0x03);
        assertEquals(0x30201, littleEndianSut.bytesToUnsignedInt(bytes, 0, 3));
        assertEquals(0x30201, littleEndianSut.bytesToUnsignedInt(bytes));

        assertEquals(0x4030201, littleEndianSut.bytesToUnsignedInt(oneToSeven, 0, 4));
        testBytesToIntFailure("A 7 byte number is not legal", littleEndianSut, oneToSeven);
    }

    private void testBytesToIntFailure(String msg, ByteUtils sut, byte[] bytes) {
        try {
            sut.bytesToUnsignedInt(bytes);
            fail(msg);
        } catch (IllegalArgumentException e) {
            // Pass
        }

        try {
            sut.bytesToUnsignedInt(bytes, 0, bytes.length);
            fail(msg);
        } catch (IllegalArgumentException e) {
            // Pass
        }
    }

    @Test
    public void testBigEndianBytesToInt() {
        byte[] bytes = ByteUtils.createByteArray(0x01);
        assertEquals(0x01, bigEndianSut.bytesToUnsignedInt(bytes, 0, 1));
        assertEquals(0x01, bigEndianSut.bytesToUnsignedInt(bytes));

        bytes = ByteUtils.createByteArray(0x01, 0x02);
        assertEquals(0x102, bigEndianSut.bytesToUnsignedInt(bytes, 0, 2));
        assertEquals(0x102, bigEndianSut.bytesToUnsignedInt(bytes));

        bytes = ByteUtils.createByteArray(0x01, 0x02, 0x03);
        assertEquals(0x10203, bigEndianSut.bytesToUnsignedInt(bytes, 0, 3));
        assertEquals(0x10203, bigEndianSut.bytesToUnsignedInt(bytes));
        testBytesToIntFailure("A 7 byte number is not legal", littleEndianSut, oneToSeven);
    }

    @Test
    public void testCommonCreateByteArray() {
        byte[] array = ByteUtils.createByteArray(1, 2, 3, 4, 5, 6, 7);
        byte[] expected = new byte[] {
                (byte) 1,
                (byte) 2,
                (byte) 3,
                (byte) 4,
                (byte) 5,
                (byte) 6,
                (byte) 7 };
        assertArrayEquals(expected, array);

        array = ByteUtils.createByteArray();
        expected = new byte[0];
        assertArrayEquals(expected, array);
    }

    @Test
    public void testArrayEqualsSubset() {
        byte[] array = ByteUtils.createByteArray(1, 2, 3, 4, 5, 6, 7);
        assertTrue(ByteUtils.arrayEqualsSubset(array, 1, 2, 3, 4, 5, 6, 7));
        // We DO NOT want to through an array index out of bounds exception!!
        assertFalse(ByteUtils.arrayEqualsSubset(array, 1, 2, 3, 4, 5, 6, 7, 8));
        assertTrue(ByteUtils.arrayEqualsSubset(array, 1, 2));
        assertTrue(ByteUtils.arrayEqualsSubset(array));
        assertFalse(ByteUtils.arrayEqualsSubset(array, 2, 3));
    }

    @Test
    public void testCommonHexToBytes() {
        assertArrayEquals(oneToSeven, ByteUtils.hexToBytes("01020304050607"));

        int byteRange = 1 << Byte.SIZE;
        byte[] expected = new byte[byteRange];
        for (int b = 0; b < byteRange; b++) {
            expected[b] = (byte) b;
        }
        String fullHexRange = fullHexRange();
        assertArrayEquals(expected, ByteUtils.hexToBytes(fullHexRange));

        fullHexRange += " not valid hex";
        assertArrayEquals(expected, ByteUtils.hexToBytes(fullHexRange, 0, byteRange * 2));

        String lastTest = "not hex" + fullHexRange;
        assertArrayEquals(expected, ByteUtils.hexToBytes(lastTest, 7, byteRange * 2));
    }

    @Test
    public void testBytesToHex() {
        String expectedString = "00ff01fe02fd03";
        byte[] test = ByteUtils.createByteArray(0x00, 0xff, 0x01, 0xfe, 0x02, 0xfd, 0x03);
        assertEquals(expectedString, ByteUtils.bytesToHex(test));

        byte[] expected = fullByteRange();
        String hex = ByteUtils.bytesToHex(expected);
        byte[] actual = ByteUtils.hexToBytes(hex);
        assertArrayEquals(expected, actual);
    }

    private String fullHexRange() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 1 << Byte.SIZE; i++) {
            String hex = Integer.toHexString(i);
            sb.append(hex.length() < 2 ? "0" + hex : hex);
        }
        return sb.toString();
    }

    private byte[] fullByteRange() {
        byte[] array = new byte[256];
        for (int i = 0; i < 256; i++) {
            array[i] = (byte) i;
        }
        return array;
    }

    @Test
    public void testBytesToInt() {
        byte[] bytes = new byte[] { (byte) 0xFF };
        assertEquals(-255, littleEndianSut.bytesToSignedInt(bytes, 0, 1));
        assertEquals(-255, bigEndianSut.bytesToSignedInt(bytes, 0, 1));

        bytes = ByteUtils.createByteArray(0x80, 0x00);
        assertEquals(-128, littleEndianSut.bytesToSignedInt(bytes, 0, 2));
        assertEquals(-32768, bigEndianSut.bytesToSignedInt(bytes, 0, 2));
    }

}
