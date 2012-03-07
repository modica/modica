package org.modica.afp.modca.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.modica.afp.modca.common.Rotation;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link Rotation}.
 */
public class RotationTestCase {

    @Test
    public void testGetters() {
        testBytes(0, Rotation.ZERO);
        testBytes(0x2D, Rotation.NINETY);
        testBytes(0x5A, Rotation.ONE_EIGHTY);
        testBytes(0x87, Rotation.TWO_SEVENTY);

        try {
            Rotation.getValue((byte) 0x10);
            fail("Exception should be thrown with invalid Rotation value");
        } catch (IllegalArgumentException iae) {
            //Pass
        }
    }

    private void testBytes(int val, Rotation rot) {
        assertArrayEquals(ByteUtils.createByteArray(val, 0), rot.getBytes());
        assertEquals(rot, Rotation.getValue((byte) val));
    }

}
