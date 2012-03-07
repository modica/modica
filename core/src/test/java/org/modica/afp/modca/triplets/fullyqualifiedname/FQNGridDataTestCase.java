package org.modica.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNFmt;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNGridData;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNType;
import org.modica.afp.modca.triplets.fullyqualifiedname.GlobalResourceId;
import org.modica.common.ByteUtils;

public class FQNGridDataTestCase extends TripletTestCase<FQNGridData> {
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

        x = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes, "Cp500")), type);
        FQNGridData y = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes, "Cp500")), type);
        FQNGridData z = new FQNGridData(length, new GlobalResourceId(new Parameters(gridBytes, "Cp500")), type);

        byte[] notEqualsGrid = new byte[gridBytes.length];
        System.arraycopy(gridBytes, 0, notEqualsGrid, 0, gridBytes.length);
        notEqualsGrid[7] = 0x05;

        FQNGridData notEqual = new FQNGridData(length, new GlobalResourceId(new Parameters(notEqualsGrid, "Cp500")), type);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetters() {
        GlobalResourceId expected = new GlobalResourceId(new Parameters(gridBytes, "Cp500"));
        assertEquals(expected, x.getGrid());
        assertEquals(FQNFmt.character_string, x.getFormat());
        assertEquals(type, x.getFQNType());
        assertEquals(length, x.getLength());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("GlobalResourceId", x.getGrid()));
        parameterTester(expectedParams, x);
    }
}
