package org.afpparser.afp.modca.structuredfields.migration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
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

    public PresentationTextDataDescriptor(StructuredFieldIntroducer introducer, Parameters params) {
        super(introducer, Collections.<Triplet>emptyList());
        byte xpBase = params.getByte();
        byte ypBase = params.getByte();
        assert xpBase == 0 && ypBase == 0;
        xAxisUnit = (int) params.getUInt(2);
        yAxisUnit = (int) params.getUInt(2);
        xAxisSize = (int) params.getUInt(3);
        yAxisSize = (int) params.getUInt(3);
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
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("X-AxisUnits", xAxisUnit));
        params.add(new ParameterAsString("Y-AxisUnits", yAxisUnit));
        params.add(new ParameterAsString("X-AxisSize", xAxisSize));
        params.add(new ParameterAsString("Y-AxisSize", yAxisSize));
        return params;
    }
}
