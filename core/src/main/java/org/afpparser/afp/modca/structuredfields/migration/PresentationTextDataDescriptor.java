package org.afpparser.afp.modca.structuredfields.migration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

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

    public PresentationTextDataDescriptor(SfIntroducer introducer, Parameters params) {
        super(introducer, Collections.<Triplet>emptyList());
        byte xpBase = params.getByte();
        byte ypBase = params.getByte();
        assert xpBase == 0 && ypBase == 0;
        xAxisUnit = params.getUInt(2);
        yAxisUnit = params.getUInt(2);
        xAxisSize = params.getUInt(3);
        yAxisSize = params.getUInt(3);
        assert params.getUInt(2) == 0; // text flags, reserved
        // TODO: add the initial text conditions parsing
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

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("X-AxisUnits", String.valueOf(xAxisUnit));
        params.put("Y-AxisUnits", String.valueOf(yAxisUnit));
        params.put("X-AxisSize", String.valueOf(xAxisSize));
        params.put("Y-AxisSize", String.valueOf(yAxisSize));
        return params;
    }
}
