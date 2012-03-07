package org.modica.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.ObjectAreaSize;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link ObjectAreaSize}.
 */
public class ObjectAreaSizeTestCase extends TripletTestCase<ObjectAreaSize> {

    private ObjectAreaSize x;

    @Before
    @Override
    public void setUp() {
        byte[] data = ByteUtils.createByteArray(2, 1, 2, 3, 4, 5, 6);
        x = new ObjectAreaSize(new Parameters(data, "Cp500"));
        ObjectAreaSize y = new ObjectAreaSize(new Parameters(data, "Cp500"));
        ObjectAreaSize z = new ObjectAreaSize(new Parameters(data, "Cp500"));

        data[1] = 2;
        ObjectAreaSize notEqual = new ObjectAreaSize(new Parameters(data, "Cp500"));

        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x10203, x.getXoaSize());
        assertEquals(0x40506, x.getYoaSize());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("X-AxisSize", x.getXoaSize()));
        expectedParams.add(new ParameterAsString("Y-AxisSize", x.getYoaSize()));
        parameterTester(expectedParams, x);
    }
}
