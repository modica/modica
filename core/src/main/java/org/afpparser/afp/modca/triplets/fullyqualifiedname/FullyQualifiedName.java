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
            return handleStringData(length, data, type, dataIndex);
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

    private static FullyQualifiedName handleStringData(int length, byte[] data, FQNType type,
            int position) throws UnsupportedEncodingException {
        switch (type) {
        case begin_resource_object_ref:
            GlobalResourceId grid = new GlobalResourceId(data, position);
            return new FQNGridData(length, grid, type);
        case data_object_internal_resource_ref:
            int undefLength = data.length - position;
            byte[] undefData = new byte[undefLength];
            System.arraycopy(data, position, undefData, 0, undefLength);
            return new FQNUndefData(length, undefData, type);
        default:
            String gid = parseString(data, position);
            return new FQNCharStringData(length, gid, type);
        }
    }
}
