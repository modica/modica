package org.afpparser.afp.ioca;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.ColorSpace;
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

    public SetExtendedBilevelImageColor(Parameters params) {
        int position = params.getPosition();
        // The length does not include the length field
        length = params.getInt(1) + 1;
        colourSpace = ColorSpace.getValue(params.getByte());
        colSize1 = (int) params.getUInt(2);
        colSize2 = (int) params.getUInt(2);
        colSize3 = (int) params.getUInt(2);
        colSize4 = (int) params.getUInt(2);
        int colourLength = length - (params.getPosition() - position);
        color = params.getByteArray(colourLength);
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
        ByteUtils byteUtils = ByteUtils.getBigEndianUtils();
        return (int) byteUtils.bytesToUnsignedInt(color);
    }

    @Override
    public String toString() {
        return "SetExtendedBilevelImageColor colourSpace=" + colourSpace;
    }

    @Override
    public String getName() {
        return "SetExtendedBilevelImageColour";
    }

    @Override
    public String getValueAsString() {
        return "ColourSpace=" + colourSpace.toString()
                + " colSize1=" + colSize1
                + " colSize2=" + colSize2
                + " colSize3=" + colSize3
                + " colSize4=" + colSize4;
    }
}
