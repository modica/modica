package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * The Set Text Color control sequence specifies a color attribute for the foreground areas of the
 * text presentation space.
 */
public class SetTextColor extends ControlSequence {

    private final Color foregroundColour;
    private final boolean precision;

    public SetTextColor(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        foregroundColour = Color.getValue(params.getByte(), params.getByte());
        if (length > 4) {
            precision = params.getByte() > 0;
        } else {
            precision = false;
        }
        if (precision && foregroundColour == null) {
            throw new IllegalStateException("An invalid colour was given in this PTOCA control" +
                    " sequence.");
        }
    }

    public enum Color {
        DEVICE_DEFAULT,
        BLUE,
        RED,
        PINK_MAGENTA,
        GREEN,
        TURQUOISE_CYAN,
        YELLOW,
        BLACK,
        BROWN,
        RESET_COLOR,
        DEFAULT_INDICATOR;

        private static Color getValue(byte byte1, byte byte2) {
            if (byte1 == (byte) 0xFF && byte2 < 0x07) {
                return higherOrderColors(byte2);
            } else {
                switch (byte2) {
                case 0x00:
                case 0x01:
                case 0x02:
                case 0x03:
                case 0x04:
                case 0x05:
                case 0x06:
                    return Color.values()[byte2];
                case 0x08:
                    return Color.values()[byte2 - 1];
                case 0x10:
                    return Color.values()[byte2 - 2];
                }
            }
            return null;
        }

        private static Color higherOrderColors(byte id) {
            switch (id) {
            case 0x07:
                return DEVICE_DEFAULT;
            case 0x08:
                return RESET_COLOR;
            case (byte) 0xFF:
                return DEFAULT_INDICATOR;
            default:
                return null;
            }
        }
    }

    @Override
    public String getValueAsString() {
        return foregroundColour.toString();
    }
}
