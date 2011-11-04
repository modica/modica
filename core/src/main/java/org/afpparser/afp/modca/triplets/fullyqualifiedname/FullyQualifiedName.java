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
        assert data[0] == FullyQualifiedName.tId.getId();
        FQNType type = FQNType.getValue(data[1]);
        String strData;
        assert type != null;
        switch (type) {
        case font_charset_name_ref:
            strData = parseString(data);
            return new FontCharSetNameRef(length, strData);
        case code_page_name_ref:
            strData = parseString(data);
            return new CodePageNameRef(length, strData);
        }
        return null;
    }

    private static String parseString(byte[] data) throws UnsupportedEncodingException {
        assert data[2] == FQNFmt.character_string.getId();
        return StringUtils.bytesToCp500(data, 3, data.length - 3);
    }
}
