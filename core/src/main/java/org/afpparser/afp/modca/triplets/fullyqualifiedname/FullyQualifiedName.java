package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletIdentifiers;
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
            throws UnsupportedEncodingException {
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
        }

        return null;
    }

    private static String parseString(byte[] data, int position)
            throws UnsupportedEncodingException {
        assert data[position++] == FQNFmt.character_string.getId();
        return StringUtils.bytesToCp500(data, position, data.length - position);
    }

    public final static class ObjectId {
        private final int length;
        private final byte[] oid;
        public static final int OID_ENCODING = 0x06;

        private ObjectId(byte[] data, int position) {
            // The first bit of the length is always set to 0
            byte oidEncoding = data[position++];
            assert oidEncoding == OID_ENCODING;
            length = data[position++] & 0x7F;
            byte[] oidData = new byte[data.length - position];
            System.arraycopy(data, position, oidData, 0, data.length - position);
            oid = decodeOid(oidData);
        }

        private byte[] decodeOid(byte[] data) {
            byte[] decodedOid = new byte[length];
            int resultIndex = 0;
            int bitCount = 1;
            int dataIndex = 0;
            boolean notLastByte = true;
            do {
                byte currentByte = data[dataIndex++];
                notLastByte = (currentByte & 0x80) > 0;
                if (!notLastByte) {
                    decodedOid[resultIndex] = currentByte;
                    return decodedOid;
                } else if (resultIndex <= length - 1) {
                    byte decodedByte = (byte) (currentByte << 8 - bitCount++);
                    byte nextByte = data[dataIndex];
                    notLastByte = (nextByte & 0x80) > 0;
                    decodedByte |= nextByte;
                    decodedOid[resultIndex++] = decodedByte;
                }
                bitCount++;
            } while (notLastByte);
            return decodedOid;
        }

        static ObjectId parse(byte[] data, int position) {
            return new ObjectId(data, position);
        }

        byte[] getOid() {
            byte[] ret = new byte[oid.length];
            System.arraycopy(oid, 0, ret, 0, oid.length);
            return ret;
        }
    }

}
