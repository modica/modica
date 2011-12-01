package org.afpparser.afp.modca.ioca;

import org.afpparser.common.ByteUtils;

/**
 * This optional self-defining field specifies a color value and defines the color space and
 * encoding for that value. This SDF is applicable only to significant image points of bilevel
 * images with zero LUT-IDs.
 */
public class SetExtendedBilevelImageColor implements SelfDefiningField {

    private final int length;
    private final ColorSpace colourSpace;
    private final int colSize1;
    private final int colSize2;
    private final int colSize3;
    private final int colSize4;
    private final byte[] color;

    public SetExtendedBilevelImageColor(byte[] data, int position) {
        int byteIndex = position;
        ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();
        // The length does not include the length field
        length = data[byteIndex++] + 1;
        colourSpace = ColorSpace.getValue(data[byteIndex++]);
        colSize1 = byteUtils.bytesToUnsignedInt(data, byteIndex, 2);
        byteIndex += 2;
        colSize2 = byteUtils.bytesToUnsignedInt(data, byteIndex, 2);
        byteIndex += 2;
        colSize3 = byteUtils.bytesToUnsignedInt(data, byteIndex, 2);
        byteIndex += 2;
        colSize4 = byteUtils.bytesToUnsignedInt(data, byteIndex, 2);
        byteIndex += 2;
        int colourLength = length - (byteIndex - position);
        color = new byte[colourLength];
        System.arraycopy(data, byteIndex, color, 0, colourLength);
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public byte getId() {
        return (byte) 0xF4;
    }

    public ColorSpace getColourSpace() {
        return colourSpace;
    }

    public int getColourSize1() {
        return colSize1;
    }

    public int getColourSize2() {
        return colSize2;
    }

    public int getColourSize3() {
        return colSize3;
    }

    public int getColourSize4() {
        return colSize4;
    }

    public int getColor() {
        ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();
        return byteUtils.bytesToUnsignedInt(color);
    }

    /**
     * A code that defines the color space and the encoding for the color specification.
     */
    public enum ColorSpace {
        /**
         * RGB color space. The color value is specified with three components. Components 1, 2, and
         * 3 are unsigned binary numbers that specify the red, green, and blue intensity values, in
         * that order. ColSize1, ColSize2, and ColSize3 are non-zero and define the number of bits
         * used to specify each component. ColSize4 is reserved and should be set to zero.
         */
        RGB(0x01),
        /**
         * CMYK color space. The color value is specified with four components. Components 1, 2, 3,
         * and 4 are unsigned binary numbers that specify the cyan, magenta, yellow, and black
         * intensity values, in that order. ColSize1, ColSize2, ColSize3, and ColSize4 are non-zero
         * and define the number of bits used to specify each component.
         */
        CMYK(0x04),
        /**
         * Highlight color space. This color space defines a request for the presentation device to
         * generate a highlight color. The color value is specified with one to three components.
         */
        HILIGHT_COLOR_SPACE(0x06),
        /** CIELAB color space. */
        CIELAB(0x08),
        /** Standard OCA color space. The color value is specified with one component.*/
        STANDARD_OCA_COLORSPACE(0x40);

        private final byte id;

        private ColorSpace(int id) {
            this.id = (byte) id;
        }

        public static ColorSpace getValue(byte id) {
            for (ColorSpace cs : ColorSpace.values()) {
                if (id == cs.id) {
                    return cs;
                }
            }
            throw new IllegalArgumentException(id + " is not a valid ColorSpace value");
        }
    }

    @Override
    public String toString() {
        return "SetExtendedBilevelImageColor colourSpace=" + colourSpace;
    }
}
