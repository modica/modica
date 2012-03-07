package org.modica.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.ParameterAsStringTestCase;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.TripletTestCase;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNFmt;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNOidData;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNType;
import org.modica.afp.modca.triplets.fullyqualifiedname.ObjectId;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link FQNOidData}.
 */
public class FQNOidDataTestCase extends TripletTestCase<FQNOidData> {

    private FQNOidData x;
    private byte[] byteData;
    private int length;
    private ObjectId oid;

    @Before
    @Override
    public void setUp() {
        byteData = ByteUtils.createByteArray(6, 1, 2, 3, 4, 5, 6, 7);
        length = 8;

        oid = new ObjectId(new Parameters(byteData, "Cp500"), length);

        x = createTestSubject(byteData, length);
        FQNOidData y = createTestSubject(byteData, length);
        FQNOidData z = createTestSubject(byteData, length);

        byteData[1] = 2;
        FQNOidData notEqual = createTestSubject(byteData, length);
        setXYZ(x, y, z, notEqual);
    }

    private FQNOidData createTestSubject(byte[] data, int length) {
        return new FQNOidData(length, new ObjectId(new Parameters(data, "Cp500"), length), FQNType.attribute_gid);
    }

    @Test
    @Override
    public void testGetParameters() {
        assertEquals(FQNType.attribute_gid, x.getFQNType());
        assertEquals(FQNFmt.oid, x.getFormat());
        assertTrue(oid.equals(x.getOid()));

        List<ParameterAsString> expectedParameters = new ArrayList<ParameterAsString>();
        expectedParameters.add(new ParameterAsString(x.getFQNType().name(), oid));
        ParameterAsStringTestCase.testParameters(expectedParameters, x.getParameters());
    }
}
