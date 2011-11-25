package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

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
    private final int length = 0x06;
    private final TripletIdentifiers tid = TripletIdentifiers.coded_graphic_character_set_global_identifier;
    private byte[] cpgidBytes = ByteUtils.createByteArray(0x00, 0x01, 0x01, 0x02);
    private byte[] ccsidBytes = ByteUtils.createByteArray(0x00, 0x00, 0x01, 0x02);

    @Before
    @Override
    public void setUp() {
        // Tests CPGID
        cgcsgid = (Cpgid) Cgcsgid.parse(cpgidBytes, length, tid);
        ccsid = (Ccsid) Cgcsgid.parse(ccsidBytes, length, tid);

        Cgcsgid y = Cgcsgid.parse(cpgidBytes, length, tid);
        Cgcsgid z = Cgcsgid.parse(cpgidBytes, length, tid);
        byte[] notEqualBytes = ByteUtils.createByteArray(0x00, 0x00, 0x01, 0x03);
        Cgcsgid notEqual = Cgcsgid.parse(notEqualBytes, length, tid);
        setXYZ(cgcsgid, y, z, notEqual);
    }

    @Test
    public void testCcsidEqualsHashCode() {
        ccsid = (Ccsid) Cgcsgid.parse(ccsidBytes, length, tid);
        Cgcsgid y = Cgcsgid.parse(ccsidBytes, length, tid);
        Cgcsgid z = Cgcsgid.parse(ccsidBytes, length, tid);
        byte[] notEqualBytes = ByteUtils.createByteArray(0x00, 0x01, 0x01, 0x03);
        Cgcsgid notEqual = Cgcsgid.parse(notEqualBytes, length, tid);
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

}
