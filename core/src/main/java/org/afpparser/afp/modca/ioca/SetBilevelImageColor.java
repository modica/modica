package org.afpparser.afp.modca.ioca;

/**
 * Sets the bi-level image colour.
 */
public class SetBilevelImageColor implements SelfDefiningField {
    private static int LENGTH = 5;

    private final byte area;
    private final NamedColor colour;

    public SetBilevelImageColor(byte[] data, int position) {
        int byteIndex = position;
        assert data[byteIndex] == (byte) 0x04;
        byteIndex++;
        area = data[byteIndex++];
        assert area == (byte) 0x00;
        assert data[byteIndex] == (byte) 0x00;
        byteIndex++;
        colour = NamedColor.getValue(data, byteIndex);
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

        public static NamedColor getValue(byte[] id, int position) {
            if (id[position] == (byte) 0xFF) {
                return getAlternateValues(id[position + 1]);
            } else if (id[position] < 0x10) {
                return NamedColor.values()[id[position + 1]];
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
}
