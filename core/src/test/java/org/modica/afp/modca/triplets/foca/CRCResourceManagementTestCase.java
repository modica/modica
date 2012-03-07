package org.modica.afp.modca.triplets.foca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.afp.modca.triplets.foca.ResourceManagement;
import org.modica.afp.modca.triplets.foca.ResourceManagement.CRCResourceManagement;
import org.modica.common.ByteUtils;

public class CRCResourceManagementTestCase extends TripletTestCase<ResourceManagement> {

    private ResourceManagement x;
    private ResourceManagement notEqual;

    @Before
    @Override
    public void setUp() {
        byte[] crcBytes = ByteUtils.createByteArray(1, 2, 3, 1);
        x = ResourceManagement.parse(new Parameters(crcBytes, "Cp500"));
        ResourceManagement y = ResourceManagement.parse(new Parameters(crcBytes, "Cp500"));
        ResourceManagement z = ResourceManagement.parse(new Parameters(crcBytes, "Cp500"));

        crcBytes[3] = 0;
        notEqual = ResourceManagement.parse(new Parameters(crcBytes, "Cp500"));
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x203, x.getResourceManagementValue());
        assertTrue(x instanceof CRCResourceManagement);
        assertTrue(((CRCResourceManagement) x).isPublic());

        assertFalse(((CRCResourceManagement) notEqual).isPublic());
    }

    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ResourceManagementValue", String.valueOf(0x203)));
        expectedParams.add(new ParameterAsString("isPublic", String.valueOf(true)));
        parameterTester(expectedParams, x);
    }

}
