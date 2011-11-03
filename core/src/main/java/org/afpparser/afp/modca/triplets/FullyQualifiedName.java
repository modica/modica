package org.afpparser.afp.modca.triplets;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.afpparser.common.StringUtils;


public abstract class FullyQualifiedName extends Triplet {
    private final int length;
    private static final TripletIdentifiers tId = TripletIdentifiers.fully_qualified_name;
    private static final Map<Byte, FQNType> fqnTypes = new HashMap<Byte, FQNType>();
    static {
        for (FQNType type : FQNType.values()) {
            fqnTypes.put(type.getId(), type);
        }
    }

    /**
     * Specified the GID format.
     */
    public enum FQNFmt {
        character_string(0x00),
        oid(0x10),
        url(0x20);

        private final byte id;

        private FQNFmt(int id) {
            this.id = (byte) id;
        }

        byte getId() {
            return id;
        }
    }

    private FullyQualifiedName(int length) {
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
    
    private static abstract class FQNWithStringData extends FullyQualifiedName {
        private final String data;

        private FQNWithStringData(int length, String data) {
            super(length);
            this.data = data;
        }
        
        public String getString() {
            return this.data;
        }

        @Override
        public FQNFmt getFormat() {
            return FQNFmt.character_string;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof FQNWithStringData)) {
                return false;
            }
            FQNWithStringData fqn = (FQNWithStringData) o;
            return this.data.equals(fqn.data)
                    && this.getFQNType() == fqn.getFQNType()
                    && this.getLength() == fqn.getLength();
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + getLength();
            result = 31 * result + getFQNType().hashCode();
            result = 31 * result + data.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return tId.name() + ", " + getFQNType() + "=" + data;
        }
    }

    public static class FontCharSetNameRef extends FQNWithStringData {
        public FontCharSetNameRef(int length, String data) {
            super(length, data);
        }

        @Override
        public FQNType getFQNType() {
            return FQNType.font_charset_name_ref;
        }
    }

    public static class CodePageNameRef extends FQNWithStringData {
        public CodePageNameRef(int length, String data) {
            super(length, data);
        }

        @Override
        public FQNType getFQNType() {
            return FQNType.code_page_name_ref;
        }
    }

    public static FullyQualifiedName parse(int length, byte[] data)
            throws UnsupportedEncodingException {
        assert data[0] == FullyQualifiedName.tId.getid();
        FQNType type = fqnTypes.get(data[1]);
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
