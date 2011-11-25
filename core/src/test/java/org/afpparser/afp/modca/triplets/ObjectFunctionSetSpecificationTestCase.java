package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

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
        byte[] tripletBytes = ByteUtils.hexToBytes("06000000000000");
        x = new ObjectFunctionSetSpecification(tripletBytes, 0, 8);
        ObjectFunctionSetSpecification y = new ObjectFunctionSetSpecification(tripletBytes, 0, 8);
        ObjectFunctionSetSpecification z = new ObjectFunctionSetSpecification(tripletBytes, 0, 8);

        tripletBytes[0] = 0x05;
        tripletBytes[3] = 0x01;
        tripletBytes[4] = 0x40;
        notEqual = new ObjectFunctionSetSpecification(tripletBytes, 0, 8);
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
}
