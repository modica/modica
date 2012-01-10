package org.afpparser.afp.modca.common;

import org.afpparser.afp.modca.Parameters;

/**
 * An enumeration of standard colours.
 */
public enum NamedColor {
    PRESENTATION_PROCESS_DEFAULT,
    BLUE,
    RED,
    MAGENTA_PINK,
    GREEN,
    CYAN_TURQUOISE,
    YELLOW,
    WHITE,
    BLACK,
    DARK_BLUE,
    ORANGE,
    PURPLE,
    DARK_GREEN,
    DARK_TURQUOISE,
    MUSTARD,
    GRAY,
    BROWN,
    COLOUR_OF_MEDIUM;

    /**
     * Get the color by reading 2 bytes within the parameters object.
     *
     * @param params the parameters
     * @return the named color as an enumeration
     */
    public static NamedColor getValue(Parameters params) {
        byte firstByte = params.getByte();
        byte secondByte = params.getByte();
        if (firstByte == (byte) 0xFF) {
            return getAlternateValues(secondByte);
        } else if (secondByte <= 0x10) {
            return NamedColor.values()[secondByte];
        }
        return null;
    }

    private static NamedColor getAlternateValues(byte id) {
        if ((id & 0xFF) <= 0x06) {
            return NamedColor.values()[id];
        } else if (id == 0x07 || id == (byte) 0xFF) {
            return PRESENTATION_PROCESS_DEFAULT;
        } else if (id == 0x08) {
            return COLOUR_OF_MEDIUM;
        }
        throw new IllegalArgumentException("Invalid Named Color ID given.");
    }
}
