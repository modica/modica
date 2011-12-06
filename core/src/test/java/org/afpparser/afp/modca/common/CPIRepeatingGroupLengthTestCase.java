package org.afpparser.afp.modca.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link CPIRepeatingGroupLength}.
 */
public class CPIRepeatingGroupLengthTestCase {

    @Test
    public void testGetValue() {
        assertEquals(CPIRepeatingGroupLength.SINGLE_BYTE,
                CPIRepeatingGroupLength.getValue((byte) 0x0A));
        assertEquals(CPIRepeatingGroupLength.DOUBLE_BYTE,
                CPIRepeatingGroupLength.getValue((byte) 0x0B));
        assertEquals(CPIRepeatingGroupLength.SINGLE_BYTE_INC_UNICODE,
                CPIRepeatingGroupLength.getValue((byte) 0xFE));
        assertEquals(CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE,
                CPIRepeatingGroupLength.getValue((byte) 0xFF));
    }
}
