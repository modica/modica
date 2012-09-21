package org.modica.afp.modca.triplets.fullyqualifiedname;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.modica.afp.modca.Parameters;

/**
 * Specified the GID format.
 */
public enum FQNFmt {
    character_string(0x00) {
        @Override
        FullyQualifiedName createFQN(FQNType type, Parameters params, int length, int dataLength)
                throws UnsupportedEncodingException {
            return FullyQualifiedName.handleStringData(type, params, length, dataLength);
        }
    },
    oid(0x10) {
        @Override
        FullyQualifiedName createFQN(FQNType type, Parameters params, int length, int dataLength) {
            ObjectId oid = new ObjectId(params, dataLength);
            return new FQNOidData(length, oid, type);
        }
    },
    url(0x20) {
        @Override
        FullyQualifiedName createFQN(FQNType type, Parameters params, int length, int dataLength)
                throws MalformedURLException, UnsupportedEncodingException {
            String url = params.getString(dataLength);
            return new FQNUrlData(length, new URL(url), type);
        }
    };

    private final byte id;

    private FQNFmt(int id) {
        this.id = (byte) id;
    }

    /**
     * Return the identity byte of this FQN format.
     *
     * @return the byte identifying the FQN format
     */
    byte getId() {
        return id;
    }

    abstract FullyQualifiedName createFQN(FQNType type, Parameters params, int length,
            int dataLength) throws UnsupportedEncodingException, MalformedURLException;

    public static FQNFmt getValue(byte id) {
        for (FQNFmt format : FQNFmt.values()) {
            if (format.id == id) {
                return format;
            }
        }
        throw new IllegalArgumentException("Invalid Fully Qualified Name format");
    }
}