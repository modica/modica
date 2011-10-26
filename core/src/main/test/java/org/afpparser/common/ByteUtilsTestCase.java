package org.afpparser.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteUtilsTestCase {

    @Test
    public void testBytesToInt() {
        // 0x0102 = 258 
        byte[] bytes = ByteUtils.createByteArray(0x01, 0x02);
        int actual = ByteUtils.bytesToInt(bytes, 0, 2);
        assertEquals(0x102, actual);
    }
    
    @Test
    public void testCreateByteArray() {
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
}
