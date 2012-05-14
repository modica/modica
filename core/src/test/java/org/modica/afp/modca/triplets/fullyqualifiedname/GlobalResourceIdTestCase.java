package org.modica.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.afp.modca.triplets.fullyqualifiedname.GlobalResourceId;
import org.modica.common.ByteUtils;

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

        x = new GlobalResourceId(new Parameters(byteData));
        GlobalResourceId y = new GlobalResourceId(new Parameters(byteData));
        GlobalResourceId z = new GlobalResourceId(new Parameters(byteData));

        byteData[0] = 2;
        notEqual = new GlobalResourceId(new Parameters(byteData));
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
