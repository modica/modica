package org.afpparser.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class ByteUtilsTestCase {

    private ByteUtils littleEndianSut = ByteUtils.newLittleEndianUtils();
    private ByteUtils bigEndianSut = ByteUtils.newBigEndianUtils();

    private byte[] oneToSeven;

    @Before
    public void setUp() {
        oneToSeven = ByteUtils.createByteArray(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    public void testLittleEndianBytesToInt() {
        byte[] bytes = ByteUtils.createByteArray(0x01);
        assertEquals(0x01, littleEndianSut.bytesToInt(bytes, 0, 1));
        assertEquals(0x01, littleEndianSut.bytesToInt(bytes));

        bytes = ByteUtils.createByteArray(0x01, 0x02);
        assertEquals(0x102, littleEndianSut.bytesToInt(bytes, 0, 2));
        assertEquals(0x102, littleEndianSut.bytesToInt(bytes));

        bytes = ByteUtils.createByteArray(0x01, 0x02, 0x03);
        assertEquals(0x10203, littleEndianSut.bytesToInt(bytes, 0, 3));
        assertEquals(0x10203, littleEndianSut.bytesToInt(bytes));

        assertEquals(0x1020304, littleEndianSut.bytesToInt(oneToSeven, 0, 4));
        testBytesToIntFailure("A 7 byte number is not legal", littleEndianSut, oneToSeven);
    }

    private void testBytesToIntFailure(String msg, ByteUtils sut, byte[] bytes) {
        try {
            sut.bytesToInt(bytes);
            fail(msg);
        } catch (IllegalArgumentException e) {
            // Pass
        }

        try {
            sut.bytesToInt(bytes, 0, bytes.length);
            fail(msg);
        } catch (IllegalArgumentException e) {
            // Pass
        }
    }
    
    @Test
    public void testBigEndianBytesToInt() {
        byte[] bytes = ByteUtils.createByteArray(0x01);
        assertEquals(0x01, bigEndianSut.bytesToInt(bytes, 0, 1));
        assertEquals(0x01, bigEndianSut.bytesToInt(bytes));

        bytes = ByteUtils.createByteArray(0x01, 0x02);
        assertEquals(0x201, bigEndianSut.bytesToInt(bytes, 0, 2));
        assertEquals(0x201, bigEndianSut.bytesToInt(bytes));

        bytes = ByteUtils.createByteArray(0x01, 0x02, 0x03);
        assertEquals(0x30201, bigEndianSut.bytesToInt(bytes, 0, 3));
        assertEquals(0x30201, bigEndianSut.bytesToInt(bytes));
    }

    @Test
    public void testCommonCreateByteArray() {
        byte[] array = ByteUtils.createByteArray(1, 2, 3, 4, 5, 6, 7);
        byte[] expected = new byte[] {
                (byte)1,
                (byte)2,
                (byte)3,
                (byte)4,
                (byte)5,
                (byte)6,
                (byte)7};
        assertArrayEquals(expected, array);

        array = ByteUtils.createByteArray();
        expected = new byte[0];
        assertArrayEquals(expected, array);
    }

    @Test
    public void testCommonHexToBytes() {
        byte[] expected = ByteUtils.createByteArray(1, 2, 3, 4, 5, 6);
    }

}
