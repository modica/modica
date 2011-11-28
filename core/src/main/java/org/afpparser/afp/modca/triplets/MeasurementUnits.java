package org.afpparser.afp.modca.triplets;

import org.afpparser.afp.modca.common.PresentationSpaceUnits;
import org.afpparser.common.ByteUtils;


/**
 * The Measurement Units triplet is used to specify the units of measure for a presentation space.
 */
public class MeasurementUnits extends Triplet {

    private static final int LENGTH = 8;
    
    private final PresentationSpaceUnits xoaBase;
    private final PresentationSpaceUnits yoaBase;
    private final int xoaUnits;
    private final int yoaUnits;

    public MeasurementUnits(byte[] data, int position) {
        int byteIndex = position;
        ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();

        xoaBase = PresentationSpaceUnits.getValue(data[byteIndex++]);
        yoaBase = PresentationSpaceUnits.getValue(data[byteIndex++]);
        xoaUnits = byteUtils.bytesToUnsignedInt(data, byteIndex, 2);
        byteIndex += 2;
        yoaUnits = byteUtils.bytesToUnsignedInt(data, byteIndex, 2);
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
        return hashCode;
    }

    @Override
    public String toString() {
        return getTid().toString() + " xoaUnits=" + xoaUnits + " yoaUnits=" + yoaUnits;
    }
}
