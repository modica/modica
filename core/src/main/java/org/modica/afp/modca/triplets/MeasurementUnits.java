package org.modica.afp.modca.triplets;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.PresentationSpaceUnits;


/**
 * The Measurement Units triplet is used to specify the units of measure for a presentation space.
 */
public class MeasurementUnits extends Triplet {

    private static final int LENGTH = 8;

    private final PresentationSpaceUnits xoaBase;
    private final PresentationSpaceUnits yoaBase;
    private final int xoaUnits;
    private final int yoaUnits;

    public MeasurementUnits(Parameters params) {
        xoaBase = PresentationSpaceUnits.getValue(params.getByte());
        yoaBase = PresentationSpaceUnits.getValue(params.getByte());
        xoaUnits = (int) params.getUInt(2);
        yoaUnits = (int) params.getUInt(2);
    }

    /**
     * Specifies the unit base for the X axis of the presentation space coordinate system.
     *
     * @return the x-axis base unit
     */
    public PresentationSpaceUnits getXoaBase() {
        return xoaBase;
    }

    /**
     * Specifies the unit base for the Y axis of the presentation space coordinate system.
     *
     * @return the y-axis base unit
     */
    public PresentationSpaceUnits getYoaBase() {
        return yoaBase;
    }

    /**
     * Specifies the number of units per unit base for the X axis of the presentation space
     * coordinate system.
     *
     * @return the x-axis length
     */
    public int getXoaUnit() {
        return xoaUnits;
    }

    /**
     * Specifies the number of units per unit base for the Y axis of the presentation space
     * coordinate system.
     *
     * @return the y-axis length
     */
    public int getYoaUnit() {
        return yoaUnits;
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.measurement_units;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MeasurementUnits)) {
            return false;
        }
        MeasurementUnits obj = (MeasurementUnits) o;
        return this.xoaBase == obj.xoaBase
                && this.yoaBase == obj.yoaBase
                && this.xoaUnits == obj.xoaUnits
                && this.yoaUnits == obj.yoaUnits;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + xoaBase.hashCode();
        hashCode = 31 * hashCode + yoaBase.hashCode();
        hashCode = 31 * hashCode + xoaUnits;
        hashCode = 31 * hashCode + yoaUnits;
        hashCode = 31 * hashCode + getTid().hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return getTid().toString() + " xoaUnits=" + xoaUnits + " yoaUnits=" + yoaUnits;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("X-AxisBase", xoaBase));
        params.add(new ParameterAsString("Y-AxisBase", yoaBase));
        params.add(new ParameterAsString("X-AxisSize", xoaUnits));
        params.add(new ParameterAsString("Y-AxisSize", yoaUnits));
        return params;
    }
}
