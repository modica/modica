package org.afpparser.afp.modca.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link EncodingScheme}.
 */
public class EncodingSchemeTestCase {

    @Test
    public void testGetValue() {
        assertEquals(EncodingScheme.NO_ENCODING, EncodingScheme.getValue((byte) 0x00));
        assertEquals(EncodingScheme.SINGLE_BYTE_NO_ENCODING, EncodingScheme.getValue((byte) 0x01));
        assertEquals(EncodingScheme.DOUBLE_BYTE_NO_ENCODING, EncodingScheme.getValue((byte) 0x02));
        assertEquals(EncodingScheme.SINGLE_BYTE_IBMPC_DATA, EncodingScheme.getValue((byte) 0x21));
        assertEquals(EncodingScheme.SINGLE_BYTE_EBCDIC, EncodingScheme.getValue((byte) 0x61));
        assertEquals(EncodingScheme.DOUBLE_BYTE_EBCDIC, EncodingScheme.getValue((byte) 0x62));
        assertEquals(EncodingScheme.DOUBLE_BYTE_UCS, EncodingScheme.getValue((byte) 0x82));
    }
}
