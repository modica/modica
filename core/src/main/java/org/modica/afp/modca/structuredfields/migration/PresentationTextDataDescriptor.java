package org.modica.afp.modca.structuredfields.migration;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;

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

    PresentationTextDataDescriptor(StructuredFieldIntroducer introducer, Parameters params) {
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
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("X-AxisUnits", xAxisUnit));
        params.add(new ParameterAsString("Y-AxisUnits", yAxisUnit));
        params.add(new ParameterAsString("X-AxisSize", xAxisSize));
        params.add(new ParameterAsString("Y-AxisSize", yAxisSize));
        return params;
    }

    public static final class PTDBuilder implements Builder {
        @Override
        public PresentationTextDataDescriptor build(StructuredFieldIntroducer intro,
                Parameters params, Context context) throws UnsupportedEncodingException,
                MalformedURLException {
            return new PresentationTextDataDescriptor(intro, params);
        }
    }
}
