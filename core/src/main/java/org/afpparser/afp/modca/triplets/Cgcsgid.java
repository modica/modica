package org.afpparser.afp.modca.triplets;

import org.afpparser.afp.modca.TripletIdentifiers;
import org.afpparser.common.ByteUtils;

/**
 * Coded Graphical Character Set Global Identifier triplet is used to establish the values of the
 * code page and character set for interpretation of all structured field parameters having a
 * CHAR data type, such as name parameters, except where such parameters define a fixed encoding.  
 */
public abstract class Cgcsgid implements Triplet {
    private static TripletIdentifiers tId = TripletIdentifiers.coded_graphic_character_set_global_identifier;
    private static final int LENGTH = 6;

    /** {@inheritDoc} */
    public int getLength() {
        return LENGTH;
    }

    /** {@inheritDoc} */
    public TripletIdentifiers getTid() {
        return tId;
    }

    public static class Cpgid extends Cgcsgid {
        private final int gcsgid;
        private final int cpgid;

        public Cpgid(int gcsgid, int cpgid) {
            this.gcsgid = gcsgid;
            this.cpgid = cpgid;
        }

        public int getGcsgid() {
            return gcsgid;
        }

        public int getCpgid() {
            return cpgid;
        }
    }

    public static class Ccsid extends Cgcsgid {
        private final int ccsid;

        public Ccsid(int ccsid) {
            this.ccsid = ccsid;
        }

        public int getCcsid() {
            return ccsid;
        }
    }

    public static Cgcsgid parse(byte[] data, int length, TripletIdentifiers tId) {
        assert LENGTH == length;
        assert Cgcsgid.tId == tId;
        ByteUtils byteUtils = ByteUtils.newLittleEndianUtils();
        int gcsgid = byteUtils.bytesToUnsignedInt(data, 0, 3);
        int ccsidOrCpgid = byteUtils.bytesToUnsignedInt(data, 3, 2);
        if (gcsgid == 0x0000) {
            return new Ccsid(ccsidOrCpgid);
        } else {
            return new Cpgid(gcsgid, ccsidOrCpgid);
        }
    }
}
