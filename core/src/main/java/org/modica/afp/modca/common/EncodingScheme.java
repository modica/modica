package org.modica.afp.modca.common;

/**
 * This parameter identifies the code page as either EBCDIC-Presentation encoded, IBM-PC-Data
 * (ASCII) encoded, or UCS-Presentation encoded. It also specifies the code points as either fixed
 * single-byte values or fixed double-byte values.
 */
public enum EncodingScheme {
    NO_ENCODING(0x00),
    SINGLE_BYTE_NO_ENCODING(0x01),
    DOUBLE_BYTE_NO_ENCODING(0x02),
    SINGLE_BYTE_IBMPC_DATA(0x21),
    SINGLE_BYTE_EBCDIC(0x61),
    DOUBLE_BYTE_EBCDIC(0x62),
    DOUBLE_BYTE_UCS(0x82);

    private final byte id;

    private EncodingScheme(int id) {
        this.id = (byte) id;
    }

    public static EncodingScheme getValue(byte id) {
        for (EncodingScheme enc : EncodingScheme.values()) {
            if (enc.id ==  id) {
                return enc;
            }
        }
        throw new IllegalArgumentException(id + " is not a valid Encoding Scheme");
    }
}
