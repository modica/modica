package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.triplets.TripletTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link GlobalResourceId}.
 * This is serious abuse of the TripletTestCase, but it does everything we want it to do...
 */
public class GlobalResourceIdTestCase extends TripletTestCase<GlobalResourceId> {

    private GlobalResourceId x;
    private GlobalResourceId notEqual;
    private byte[] byteData;

    @Before
    @Override
    public void setUp() {
        byteData = ByteUtils.createByteArray(1, 2, 3, 4, 5, 6, 7, 8);

        x = new GlobalResourceId(new Parameters(byteData, "Cp500"));
        GlobalResourceId y = new GlobalResourceId(new Parameters(byteData, "Cp500"));
        GlobalResourceId z = new GlobalResourceId(new Parameters(byteData, "Cp500"));

        byteData[0] = 2;
        notEqual = new GlobalResourceId(new Parameters(byteData, "Cp500"));
        setXYZ(x, y, z, notEqual);
    }

    @Test
    @Override
    public void testGetParameters() {
        assertEquals(0x102, x.getGcsgid());
        assertEquals(0x304, x.getCpgid());
        assertEquals(0x506, x.getFgid());
        assertEquals(0x708, x.getFontWidth());

        assertEquals(0x202, notEqual.getGcsgid());
    }

}
