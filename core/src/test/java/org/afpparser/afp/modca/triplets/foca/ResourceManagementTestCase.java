package org.afpparser.afp.modca.triplets.foca;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.triplets.TripletTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link ResourceManagement}.
 */
public class ResourceManagementTestCase extends TripletTestCase<ResourceManagement> {

    private ResourceManagement x;
    private ResourceManagement notEqual;

    @Before
    @Override
    public void setUp() {
        byte[] data = ByteUtils.createByteArray(2,
                3, 4, 5, 6, // rmValue
                0xF3, // year1
                0xF1, 0xF2, // year2
                0xF3, 0xF4, 0xF5, // day
                0xF3, 0xF4, //hour
                0xF5, 0xF6, //minute
                0xF1, 0xF2, //second
                0xF9, 0xF9); // second / 10
        x = ResourceManagement.parse(new Parameters(data, "Cp500"));
        ResourceManagement y = ResourceManagement.parse(new Parameters(data, "Cp500"));
        ResourceManagement z = ResourceManagement.parse(new Parameters(data, "Cp500"));

        data[0] = 0x01;

        notEqual = ResourceManagement.parse(new Parameters(data, "Cp500"));
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x3040506, x.getResourceManagementValue());
        assertEquals(0x304, notEqual.getResourceManagementValue());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();

        String expectedString = "year=" + 2312 + " day=" + 345 + " hour=" + 34
                + " minute=" + 56;
        expectedParams.add(new ParameterAsString("Date", expectedString));
        parameterTester(expectedParams, x);
    }
}
