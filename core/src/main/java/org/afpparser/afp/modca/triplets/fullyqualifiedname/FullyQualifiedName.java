package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletIdentifiers;
import org.afpparser.common.ByteUtils;
import org.afpparser.common.StringUtils;

/**
 * An abstract class that all Fully Qualified Name triplets inherit from.
 */
public abstract class FullyQualifiedName extends Triplet {
    private final int length;
    private static final TripletIdentifiers tId = TripletIdentifiers.fully_qualified_name;

    FullyQualifiedName(int length) {
        this.length = length;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public TripletIdentifiers getTid() {
        return tId;
    }

    /**
     * Returns the type of Fully Qualified Name triplet this object represents.
     *
     * @return the fully qualified name type
     */
    public abstract FQNType getFQNType();

    /**
     * Each Fully Qualified Name object is one of three types enumerated in {@link FQNFmt}, this
     * returns the type of this FQN.
     *
     * @return the FQN format type
     */
    public abstract FQNFmt getFormat();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    public static FullyQualifiedName parse(int length, byte[] data)
            throws UnsupportedEncodingException, MalformedURLException {
        int dataIndex = 0;
        byte tripletId = data[dataIndex++];
        assert tripletId == FullyQualifiedName.tId.getId();
        FQNType type = FQNType.getValue(data[dataIndex++]);
        FQNFmt format = FQNFmt.getValue(data[dataIndex++]);
        assert type != null;
        switch (format) {
        case character_string:
            String strData = parseString(data, dataIndex);
            return new FQNCharStringData(length, strData, type);
        case oid:
            ObjectId oid = new ObjectId(data, dataIndex);
            return new FQNOidData(length, oid, type);
        case url:
            String url = parseString(data, dataIndex);
            return new FQNUrlData(length, new URL(url), type);
        default:
            throw new IllegalStateException("The Fully Qualified Name data type is unknown");
        }
    }

    private static String parseString(byte[] data, int position)
            throws UnsupportedEncodingException {
        return StringUtils.bytesToCp500(data, position, data.length - position);
    }

    /**
     * The GID is an ASN.1 Object Identifier (OID), defined in ISO/IEC 8824:1990(E). The data type
     * is CODE. The OID is encoded using the Basic Encoding Rules for ASN.1 specified in ISO/IEC
     * 8825:1990(E). 
     */
    public final static class ObjectId {
        private final byte[] oid;
        public static final int OID_ENCODING = 0x06;

        private ObjectId(byte[] data, int position) {
            // The first bit of the length is always set to 0
            byte oidEncoding = data[position];
            assert oidEncoding == OID_ENCODING;
            oid = new byte[data.length - position];
            System.arraycopy(data, position, oid, 0, data.length - position);
        }

        byte[] getOid() {
            byte[] ret = new byte[oid.length];
            System.arraycopy(oid, 0, ret, 0, oid.length);
            return ret;
        }
    }

    /**
     * The global resource identifier (GRID) is an eight-byte binary identifier used to reference
     * a coded font.
     */
    public final static class GlobalResourceId {
        private final int gcsgid;
        private final int cpgid;
        private final int fgid;
        private final int fontWidth;

        private GlobalResourceId(byte[] data, int position) {
            ByteUtils utils = ByteUtils.newLittleEndianUtils();
            gcsgid = utils.bytesToUnsignedInt(data, position += 2, 2);
            cpgid = utils.bytesToUnsignedInt(data, position += 2, 2);
            fgid = utils.bytesToUnsignedInt(data, position += 2, 2);
            fontWidth = utils.bytesToUnsignedInt(data, position += 2, 2);
        }

        public int getGcsgid() {
            return gcsgid;
        }

        public int getCpgid() {
            return cpgid;
        }

        public int getFgid() {
            return fgid;
        }

        public int getFontWidth() {
            return fontWidth;
        }
    }
}
