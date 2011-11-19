package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

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

    //TODO: This may need to be pulled out into it's own class i.e. FQNHandler
    public static FullyQualifiedName parse(byte[] data, int position, int length)
            throws UnsupportedEncodingException, MalformedURLException {
        int dataIndex = position;
        byte tripletId = data[dataIndex++];
        assert tripletId == FullyQualifiedName.tId.getId();
        FQNType type = FQNType.getValue(data[dataIndex++]);
        FQNFmt format = FQNFmt.getValue(data[dataIndex++]);
        assert type != null;
        // the length field is included in the length of the triplet
        int dataLength = length - (dataIndex - position) - 1;
        switch (format) {
        case character_string:
            return handleStringData(type, data, dataIndex, dataLength, length);
        case oid:
            ObjectId oid = new ObjectId(data, dataIndex, dataLength);
            return new FQNOidData(length, oid, type);
        case url:
            String url = parseString(data, dataIndex, dataLength);
            return new FQNUrlData(length, new URL(url), type);
        default:
            throw new IllegalStateException("The Fully Qualified Name data type is unknown");
        }
    }

    private static String parseString(byte[] data, int position, int length)
            throws UnsupportedEncodingException {
        return StringUtils.bytesToCp500(data, position, length);
    }

    private static FullyQualifiedName handleStringData(FQNType type, byte[] data, int position,
            int length, int fieldLength) throws UnsupportedEncodingException {
        switch (type) {
        case begin_resource_object_ref:
            GlobalResourceId grid = new GlobalResourceId(data, position);
            return new FQNGridData(fieldLength, grid, type);
        case data_object_internal_resource_ref:
            int undefLength = data.length - position;
            byte[] undefData = new byte[undefLength];
            System.arraycopy(data, position, undefData, 0, undefLength);
            return new FQNUndefData(fieldLength, undefData, type);
        default:
            String gid = parseString(data, position, length);
            return new FQNCharStringData(fieldLength, gid, type);
        }
    }
}
