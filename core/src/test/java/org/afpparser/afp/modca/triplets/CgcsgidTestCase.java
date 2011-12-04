package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.triplets.Cgcsgid.Ccsid;
import org.afpparser.afp.modca.triplets.Cgcsgid.Cpgid;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link Cgcsgid}.
 */
public class CgcsgidTestCase extends TripletTestCase<Cgcsgid> {
    private Cpgid cgcsgid;
    private Ccsid ccsid;
    private final TripletIdentifiers tid = TripletIdentifiers.coded_graphic_character_set_global_identifier;
    private final Parameters cpgidBytes = new Parameters(ByteUtils.createByteArray(0x00, 0x01,
            0x01, 0x02));
    private final Parameters ccsidBytes = new Parameters(ByteUtils.createByteArray(0x00, 0x00,
            0x01, 0x02));

    @Before
    @Override
    public void setUp() {
        // Tests CPGID
        cgcsgid = (Cpgid) getValue(cpgidBytes);
        ccsid = (Ccsid) getValue(ccsidBytes);

        Cgcsgid y = getValue(cpgidBytes);
        Cgcsgid z = getValue(cpgidBytes);
        Parameters notEqualBytes = new Parameters(ByteUtils.createByteArray(0x00, 0x00, 0x01, 0x03));
        Cgcsgid notEqual = Cgcsgid.parse(notEqualBytes);
        setXYZ(cgcsgid, y, z, notEqual);
    }

    @Test
    public void testCcsidEqualsHashCode() {
        ccsid = (Ccsid) getValue(ccsidBytes);
        Cgcsgid y = getValue(ccsidBytes);
        Cgcsgid z = getValue(ccsidBytes);
        Parameters notEqualBytes = new Parameters(ByteUtils.createByteArray(0x00, 0x01, 0x01, 0x03));
        Cgcsgid notEqual = getValue(notEqualBytes);
        setXYZ(ccsid, y, z, notEqual);
        testEqualsHashCode();
    }

    @Test
    public void testGetters() {
        // cpgid getters
        assertEquals(0x06, cgcsgid.getLength());
        assertEquals(0x0001, cgcsgid.getGcsgid());
        assertEquals(0x0102, cgcsgid.getCpgid());
        assertEquals(tid, cgcsgid.getTid());

        assertEquals(0x06, ccsid.getLength());
        assertEquals(0x0102, ccsid.getCcsid());
        assertEquals(tid, ccsid.getTid());
    }

    private Cgcsgid getValue(Parameters params) {
        Cgcsgid value = Cgcsgid.parse(params);
        params.skipTo(0);
        return value;
    }
}
