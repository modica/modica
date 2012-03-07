package org.modica.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.afp.modca.triplets.fullyqualifiedname.ObjectId;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link ObjectId}.
 */
public class ObjectIdTestCase extends TripletTestCase<ObjectId> {

    private ObjectId x;
    private ObjectId notEqual;
    private byte[] byteData;

    @Before
    @Override
    public void setUp() {
        byteData = ByteUtils.createByteArray(6, 2, 3, 4, 5, 6, 7, 8, 9);
        x = new ObjectId(new Parameters(byteData, "Cp500"), byteData.length);
        ObjectId y = new ObjectId(new Parameters(byteData, "Cp500"), byteData.length);
        ObjectId z = new ObjectId(new Parameters(byteData, "Cp500"), byteData.length);

        byteData[1] = 1;
        notEqual = new ObjectId(new Parameters(byteData, "Cp500"), byteData.length);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    @Override
    public void testGetParameters() {
        byte[] expected = new byte[byteData.length - 1];
        System.arraycopy(byteData, 1, expected, 0, byteData.length - 1);
        assertArrayEquals(expected, notEqual.getOid());
    }
}
