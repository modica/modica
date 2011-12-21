package org.afpparser.afp.ioca;

import org.afpparser.afp.modca.Parameters;

/**
 * Sets the bi-level image colour.
 */
public class SetBilevelImageColor implements SelfDefiningField {
    private static int LENGTH = 5;

    private final byte area;
    private final NamedColor colour;

    public SetBilevelImageColor(Parameters params) {
        byte length = params.getByte();
        assert length == 0x04;
        area = params.getByte();
        assert area == 0x00;
        byte reserved = params.getByte();
        assert reserved == (byte) 0x00;
        colour = NamedColor.getValue(params);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public byte getId() {
        return (byte) 0xF6;
    }

    /**
     * Return the colour.
     *
     * @return the colour
     */
    public NamedColor getColour() {
        return colour;
    }

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

        public static NamedColor getValue(Parameters params) {
            int firstByte = params.getByte();
            if (firstByte == (byte) 0xFF) {
                return getAlternateValues(params.getByte());
            } else if (firstByte < 0x10) {
                return NamedColor.values()[params.getByte()];
            }
            throw new IllegalArgumentException("Invalid Named Color ID given.");
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

    @Override
    public String toString() {
        return "SetBilevelImageColor area=" + area + " colour=" + colour;
    }

    @Override
    public String getName() {
        return "SetBilevelImageColor";
    }

    @Override
    public String getValueAsString() {
        return colour.toString();
    }
}
