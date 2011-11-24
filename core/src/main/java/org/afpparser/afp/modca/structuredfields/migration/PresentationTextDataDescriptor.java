package org.afpparser.afp.modca.structuredfields.migration;

import java.util.Collections;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.ByteUtils;

/**
 * The Presentation Text Data Descriptor Format 1 structured field specifies the size of a text
 * object presentation space and the measurement units used for the size and for all linear
 * measurements within the text object.
 */
public class PresentationTextDataDescriptor extends StructuredFieldWithTriplets {

    private final int xAxisUnit;
    private final int yAxisUnit;
    private final int xAxisSize;
    private final int yAxisSize;

    public PresentationTextDataDescriptor(SfIntroducer introducer, byte[] sfData) {
        super(introducer, Collections.<Triplet>emptyList());
        int position = 2;
        assert sfData[0] == 0 && sfData[1] == 0;
        ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();
        xAxisUnit = byteUtils.bytesToUnsignedInt(sfData, position, 2);
        position += 2;
        yAxisUnit = byteUtils.bytesToUnsignedInt(sfData, position, 2);
        position += 3; // the first byte is ignored
        xAxisSize = byteUtils.bytesToUnsignedInt(sfData, position, 2);
        position += 3; // the first byte is ignored
        yAxisSize = byteUtils.bytesToUnsignedInt(sfData, position, 2);
    }

    /**
     * Specifies the number of units per unit base for the X axis of the text presentation space.
     *
     * @return the unit base for the x-axis
     */
    public int getXptUnits() {
        return xAxisUnit;
    }

    /**
     * Specifies the number of units per unit base for the y axis of the text presentation space.
     *
     * @return the unit base for the y-axis
     */
    public int getYptUnits() {
        return yAxisUnit;
    }

    /**
     * Specifies the extent along the X axis of the text presentation space. This must be equal to
     * the extent along the X axis of the including page or overlay presentation space.
     *
     * @return the size of the x-axis
     */
    public int getXptSize() {
        return xAxisSize;
    }

    /**
     * Specifies the extent along the Y axis of the text presentation space. This must be equal to
     * the extent along the Y axis of the including page or overlay presentation space.
     *
     * @return the size of the y-axis
     */
    public int getYptSize() {
        return yAxisSize;
    }

    @Override
    public String toString() {
        return "PTD: x-axis size:" + xAxisSize + " y-axis size:" + yAxisSize;
    }
}
