package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.triplets.AbstractTripletTest;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

public class FQNGridDataTestCase extends AbstractTripletTest<FQNGridData> {
    private FQNGridData x;
    private byte[] gridBytes;
    private FQNType type;
    private int length;

    @Before
    @Override
    public void setUp() {
        gridBytes = ByteUtils.createByteArray(00, 01, 00, 02, 00, 03, 00, 04);
        type = FQNType.font_family_name;
        length = 0;

        x = new FQNGridData(length, new GlobalResourceId(gridBytes, 0), type);
        FQNGridData y = new FQNGridData(length, new GlobalResourceId(gridBytes, 0), type);
        FQNGridData z = new FQNGridData(length, new GlobalResourceId(gridBytes, 0), type);

        byte[] notEqualsGrid = new byte[gridBytes.length];
        System.arraycopy(gridBytes, 0, notEqualsGrid, 0, gridBytes.length);
        notEqualsGrid[7] = 0x05;

        FQNGridData notEqual = new FQNGridData(length, new GlobalResourceId(notEqualsGrid, 0), type);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetters() {
        GlobalResourceId expected = new GlobalResourceId(gridBytes, 0);
        assertEquals(expected, x.getGrid());
        assertEquals(FQNFmt.character_string, x.getFormat());
        assertEquals(type, x.getFQNType());
        assertEquals(length, x.getLength());
    }

}
