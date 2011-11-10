package org.afpparser.afp.modca.triplets;

import org.afpparser.common.ByteUtils;

/**
 * Coded Graphical Character Set Global Identifier triplet is used to establish the values of the
 * code page and character set for interpretation of all structured field parameters having a
 * CHAR data type, such as name parameters, except where such parameters define a fixed encoding.  
 */
public abstract class Cgcsgid extends Triplet {
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

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Cpgid)) {
                return false;
            }
            Cpgid obj = (Cpgid) o;
            return this.gcsgid == obj.gcsgid
                    && this.cpgid == obj.cpgid;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + gcsgid;
            result = 31 * result + cpgid;
            return result;
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

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Ccsid)) {
                return false;
            }
            Ccsid obj = (Ccsid) o;
            return this.ccsid == obj.ccsid;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + ccsid;
            return result;
        }
    }

    public static Cgcsgid parse(byte[] data, int length, TripletIdentifiers tId) {
        assert LENGTH == length;
        assert Cgcsgid.tId == tId;
        ByteUtils byteUtils = ByteUtils.newLittleEndianUtils();
        int gcsgid = byteUtils.bytesToUnsignedInt(data, 0, 2);
        int ccsidOrCpgid = byteUtils.bytesToUnsignedInt(data, 2, 2);
        if (gcsgid == 0x0000) {
            return new Ccsid(ccsidOrCpgid);
        } else {
            return new Cpgid(gcsgid, ccsidOrCpgid);
        }
    }
}
