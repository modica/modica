package org.modica.afp.ptoca;

import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.ColorSpace;
import org.modica.common.ByteUtils;

/**
 * The Set Extended Text Color control sequence specifies a color value and defines the color space
 * and encoding for that value. The specified color value is applied to foreground areas of the text
 * presentation space.
 */
public class SetExtendedTextColor extends ControlSequence {

    private final ColorSpace colourSpace;
    private final int colourSize1;
    private final int colourSize2;
    private final int colourSize3;
    private final int colourSize4;
    private final byte[] color;

    public SetExtendedTextColor(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        int position = params.getPosition();
        params.getByte(); // reserved
        colourSpace = ColorSpace.getValue(params.getByte());
        params.skip(4);
        colourSize1 = (int) params.getUInt(1);
        colourSize2 = (int) params.getUInt(1);
        colourSize3 = (int) params.getUInt(1);
        colourSize4 = (int) params.getUInt(1);
        // length and type fields are included in the length
        int colourLength = length - (params.getPosition() - position) - 2;
        color = params.getByteArray(colourLength);
    }

    public ColorSpace getColourSpace() {
        return colourSpace;
    }

    public int getColourSize1() {
        return colourSize1;
    }

    public int getColourSize2() {
        return colourSize2;
    }

    public int getColourSize3() {
        return colourSize3;
    }

    public int getColourSize4() {
        return colourSize4;
    }

    public long getColor() {
        ByteUtils byteUtils = ByteUtils.getBigEndianUtils();
        return byteUtils.bytesToUnsignedInt(color);
    }

    @Override
    public String getValueAsString() {
        return "ColourSpace=" + colourSpace.toString()
                + " colSize1=" + colourSize1
                + " colSize2=" + colourSize2
                + " colSize3=" + colourSize3
                + " colSize4=" + colourSize4;
    }
}
