package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.triplets.ObjectFunctionSetSpecification.ObjectType;
import org.afpparser.afp.modca.triplets.ObjectFunctionSetSpecification.OcaFunctionSet;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link ObjectFunctionSetSpecification}.
 */
public class ObjectFunctionSetSpecificationTestCase extends
        TripletTestCase<ObjectFunctionSetSpecification> {

    private ObjectFunctionSetSpecification x;
    private ObjectFunctionSetSpecification notEqual;

    @Before
    @Override
    public void setUp() {
        byte[] tripletBytes = ByteUtils.hexToBytes("10210600000000000000");
        Parameters params = new Parameters(tripletBytes, "Cp500");
        params.skip(2);
        x = new ObjectFunctionSetSpecification(params, 8);
        params.skipTo(2);
        ObjectFunctionSetSpecification y = new ObjectFunctionSetSpecification(params, 8);
        params.skipTo(2);
        ObjectFunctionSetSpecification z = new ObjectFunctionSetSpecification(params, 8);

        tripletBytes[2] = 0x05;
        tripletBytes[5] = 0x01;
        tripletBytes[6] = 0x40;
        params = new Parameters(tripletBytes, "Cp500");
        params.skipTo(2);
        notEqual = new ObjectFunctionSetSpecification(params, 8);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(8, x.getLength());
        assertEquals(8, notEqual.getLength());

        assertEquals(ObjectType.IMAGE, x.getObjType());
        assertEquals(ObjectType.RETIRED_OBJECT, notEqual.getObjType());

        assertEquals(0, x.getArchVersion());
        assertEquals(0, notEqual.getArchVersion());

        assertEquals(0, x.getDcaFunctionSet());
        assertEquals(1, notEqual.getDcaFunctionSet());

        assertEquals(OcaFunctionSet.PRESENTATION_TEXT_DATA_PT1, x.getOcaFunctionSet());
        assertEquals(OcaFunctionSet.GRAPHICS_DATA_DR2V0, notEqual.getOcaFunctionSet());
    }

    @Test
    @Override
    public void testValueAsString() {
        // TODO Auto-generated method stub
        String expectedString = "ObjectType=" + x.getObjType().toString()
                + " ArchVersion=" + ByteUtils.bytesToHex(x.getArchVersion())
                + " MODCAFunctionSet=" + x.getDcaFunctionSet()
                + " OCAFuntionSet=" + x.getOcaFunctionSet().toString();
        assertEquals(expectedString, x.valueToString());
    }
}
