package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.common.PresentationSpaceUnits;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link MeasurementUnits}.
 */
public class MeasurementUnitsTestCase extends TripletTestCase<MeasurementUnits> {

    private MeasurementUnits x;
    private MeasurementUnits notEqual;

    @Before
    @Override
    public void setUp() {
        byte[] data = ByteUtils.createByteArray(0, 0, 1, 2, 3, 4);
        x = new MeasurementUnits(data, 0);
        MeasurementUnits y = new MeasurementUnits(data, 0);
        MeasurementUnits z = new MeasurementUnits(data, 0);

        data[0] = 1;
        data[1] = 1;
        notEqual = new MeasurementUnits(data, 0);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(PresentationSpaceUnits.INCHES_10, x.getXoaBase());
        assertEquals(PresentationSpaceUnits.INCHES_10, x.getYoaBase());
        assertEquals(0x0102, x.getXoaUnit());
        assertEquals(0x0304, x.getYoaUnit());

        assertEquals(PresentationSpaceUnits.CENTIMETRE_10, notEqual.getXoaBase());
        assertEquals(PresentationSpaceUnits.CENTIMETRE_10, notEqual.getYoaBase());
        assertEquals(0x0102, notEqual.getXoaUnit());
        assertEquals(0x0304, notEqual.getYoaUnit());
    }
}