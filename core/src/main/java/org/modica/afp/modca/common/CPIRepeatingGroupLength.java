package org.modica.afp.modca.common;


/**
 * The length of the CPI repeating group is dependent on the length of the code point as defined
 * by the Encoding Scheme parameter in the CPD Structured Field. A single-byte code point would
 * result in a CPI length of X'0A', while a double-byte code point would result in a length of
 * X'0B'.
 * <p>
 * When GCGID-to-Unicode mappings are provided (values X'FE' or X'FF'), the length of each CPI
 * repeating group can vary depending on how many Unicode scalar values are associated with each
 * code point.
 * </p>
 */
public enum CPIRepeatingGroupLength {
    SINGLE_BYTE(0x0A, false),
    DOUBLE_BYTE(0x0B, false),
    SINGLE_BYTE_INC_UNICODE(0xFE, true),
    DOUBLE_BYTE_INC_UNICODE(0xFF, true);

    private final byte id;
    private final boolean isUnicode;

    private CPIRepeatingGroupLength(int id, boolean isUnicode) {
        this.id = (byte) id;
        this.isUnicode = isUnicode;
    }

    public static CPIRepeatingGroupLength getValue(byte id) {
        for (CPIRepeatingGroupLength len : CPIRepeatingGroupLength.values()) {
            if (len.id == id) {
                return len;
            }
        }
        throw new IllegalArgumentException(id + " is not a valid CPI Repeating Group Length.");
    }

    public boolean isUnicode() {
        return isUnicode;
    }
}
