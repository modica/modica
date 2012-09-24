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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test case for {@link Parameters}.
 */
public class ParametersTestCase {

    private Parameters sut;
    private byte[] byteArray;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        ByteBuffer bb = ByteBuffer.allocate(20);
        bb.put(ByteUtils.createByteArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0xff, 0xfe, 0xfd));
        bb.put("Tester".getBytes("Cp500"));
        byteArray = bb.array();
        sut = new Parameters(byteArray, 500);
    }

    @Test
    public void testsInSpec() {
        byte[] testData = ByteUtils.createByteArray(
                0x7F, 0xFE,
                0x7F, 0xFF, 0xFF, 0xFE,
                0x7F, 0xFF, 0xFF, 0xFF, 0xFF, 0xFE,
                0x7F, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFE);
        Parameters params = new Parameters(testData, 500);
        assertEquals(127, params.getInt(1));
        assertEquals(254, params.getUInt(1));

        assertEquals(32767, params.getInt(2));
        assertEquals(65534, params.getUInt(2));

        assertEquals(8388607, params.getInt(3));
        assertEquals(16777214, params.getUInt(3));

        assertEquals(2147483647, params.getInt(4));
        assertEquals(4294967294L, params.getUInt(4));
    }

    @Test
    public void testGetUInt() {
        assertEquals(0x0, sut.getUInt(1));
        assertEquals(1, sut.getPosition());
        assertEquals(0x102, sut.getUInt(2));
        assertEquals(3, sut.getPosition());
        assertEquals(0x30405, sut.getUInt(3));
        assertEquals(6, sut.getPosition());
        assertEquals(0x6070809, sut.getUInt(4));
        assertEquals(10, sut.getPosition());
        assertEquals(0x0AFFFEFD, sut.getUInt(4));
        assertEquals(14, sut.getPosition());
    }

    @Test
    public void testGetInt() {
        assertEquals(0x0, sut.getInt(1));
        assertEquals(1, sut.getPosition());
        assertEquals(0x102, sut.getInt(2));
        assertEquals(3, sut.getPosition());
        assertEquals(0x30405, sut.getInt(3));
        assertEquals(6, sut.getPosition());
        assertEquals(0x6070809, sut.getInt(4));
        assertEquals(10, sut.getPosition());
        assertEquals(0x0A, sut.getInt(1));
        assertEquals(11, sut.getPosition());
        assertEquals(-16776957, sut.getInt(3));
        assertEquals(14, sut.getPosition());
    }

    @Test
    public void testGetByte() {
        for (int i = 0; i < byteArray.length; i++) {
            assertEquals(byteArray[i], sut.getByte());
            assertEquals(i + 1, sut.getPosition());
        }
    }

    @Test
    public void testPeekByte() {
        for (int i = 0; i < byteArray.length; i++) {
            assertEquals(byteArray[0], sut.peekByte());
            assertEquals(0, sut.getPosition());
        }
    }

    @Test
    public void testGetByteArray() {
        sut.skip(2);
        byte[] actual = sut.getByteArray(0, 3);
        byte[] expected = ByteUtils.createByteArray(0, 1, 2);
        assertArrayEquals(expected, actual);
        assertEquals(2, sut.getPosition());

        actual = sut.getByteArray(3);
        expected = ByteUtils.createByteArray(2, 3, 4);
        assertArrayEquals(expected, actual);
        assertEquals(5, sut.getPosition());
    }

    @Test
    public void testGetString() throws UnsupportedEncodingException {
        assertEquals("Tester", sut.getStringAt(14, 6));
        assertEquals(0, sut.getPosition());

        sut.skip(14);
        assertEquals("Tester", sut.getString(6));
        assertEquals(20, sut.getPosition());

        sut.skipTo(0);
        assertEquals("Tester", sut.getStringAt(14, 6, 500));
        assertEquals(0, sut.getPosition());

        sut.skipTo(14);
        assertEquals("Tester", sut.getString(6, 500));
        assertEquals(20, sut.getPosition());
    }

    @Test
    public void testSizeAndPosition() {
        assertEquals(byteArray.length, sut.size());
        try {
            sut.skip(21);
            fail("Should not be able to skip farther than the size of the array");
        } catch (IllegalArgumentException e) {
            // PASS
            // check that the skip didn't change the state of the object
            assertEquals(0, sut.getPosition());
        }

        try {
            sut.skip(-1);
            fail("Should not be able to skip to a negative index");
        } catch (IllegalArgumentException e) {
            // PASS
            // check that the skip didn't change the state of the object
            assertEquals(0, sut.getPosition());
        }

        try {
            sut.skipTo(21);
            fail("Should not be able to skip farther than the size of the array");
        } catch (IllegalArgumentException e) {
            // PASS
            // check that the skip didn't change the state of the object
            assertEquals(0, sut.getPosition());
        }

        try {
            sut.skipTo(-1);
            fail("Should not be able to skip to a negative index");
        } catch (IllegalArgumentException e) {
            // PASS
            // check that the skip didn't change the state of the object
            assertEquals(0, sut.getPosition());
        }
    }
}
