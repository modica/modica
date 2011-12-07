package org.afpparser.afp.modca.common;

/**
 * The flags on a GCGID that describe its attributes.
 */
public enum GraphicalCharacterUseFlags {
    INVALID_CODED_CHARACTER,
    NO_PRESENTATION,
    NO_INCREMENT;

    private final byte bitMask;

    private GraphicalCharacterUseFlags() {
        bitMask = (byte) (1 << 7 - this.ordinal());
    }

    /**
     * The Invalid Coded Character parameter specifies that the associated coded graphic character
     * is not valid and should not be used for processing. If this flag is off (0), the coded
     * graphic character is valid. If this flag is on (1), the coded graphic character is not valid.
     *
     * @param flag the byte flag
     * @return true if the invalid coded character flag is set
     */
    public static boolean isInvalidCodedCharacter(byte flag) {
        return (flag & INVALID_CODED_CHARACTER.bitMask) != 0;
    }

    /**
     * The No Increment parameter specifies that the character increment for the corresponding coded
     * character should be ignored. If this flag is off (0), the coded character is incrementing. If
     * this flag is on (1), the coded character is non-incrementing.
     *
     * @param flag the byte flag
     * @return true if the no presentation flag is set
     */
    public static boolean isNoPresentation(byte flag) {
        return (flag & NO_PRESENTATION.bitMask) != 0;
    }

    /**
     * The No Presentation parameter specifies that the corresponding coded character should be
     * ignored. If this flag is off (0), the coded character is presenting. If this flag is on (1),
     * the coded character is non-presenting.
     *
     * @param flag the byte flag
     * @return true if the no increment flag is set
     */
    public static boolean isNoIncrement(byte flag) {
        return (flag & NO_INCREMENT.bitMask) != 0;
    }
}
