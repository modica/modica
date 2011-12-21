package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.afpparser.afp.modca.triplets.TripletIdentifiers;
import org.afpparser.afp.modca.triplets.TripletTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link FQNUndefData}.
 */
public class FQNUndefDataTestCase extends TripletTestCase<FQNUndefData> {
    private FQNUndefData x;
    private final int length = 1;
    private final byte[] undefData = ByteUtils.createByteArray(0, 1, 2, 3, 4, 5, 6);
    private final FQNType type = FQNType.begin_document_index_ref;
    private final byte[] notEqualData = ByteUtils.createByteArray(0, 0, 2, 3, 4, 5, 6);

    @Before
    @Override
    public void setUp() {
        x = new FQNUndefData(length, undefData, type);
        FQNUndefData y = new FQNUndefData(length, undefData, type);
        FQNUndefData z = new FQNUndefData(length, undefData, type);
        FQNUndefData notEqual = new FQNUndefData(length, notEqualData, type);
        setXYZ(x, y, z, notEqual);
    }

    public void testGetters() {
        assertEquals(1, x.getLength());
        assertEquals(FQNFmt.character_string, x.getFormat());
        assertEquals(type, x.getFQNType());
        assertEquals(TripletIdentifiers.fully_qualified_name, x.getTid());
        assertEquals(undefData, x.getUndefData());
        // MUST make sure that when data is retrieved, it is not possible to mutate the original
        // data. Since we return a byte array (which are mutable), we need to make sure a different
        // object is returned.
        assertFalse(undefData == x.getUndefData());
    }

    @Test
    @Override
    public void testGetValueAsString() {
        assertEquals("Undefined Data", x.getValueAsString());
    }

}
