package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import java.util.Arrays;

import org.afpparser.afp.modca.Parameters;

/**
 * The GID is an ASN.1 Object Identifier (OID), defined in ISO/IEC 8824:1990(E). The data type
 * is CODE. The OID is encoded using the Basic Encoding Rules for ASN.1 specified in ISO/IEC
 * 8825:1990(E).
 */
public final class ObjectId {
    private final byte[] oid;
    public static final int OID_ENCODING = 0x06;

    ObjectId(Parameters params, int length) {
        // The first bit of the length is always set to 0
        byte oidEncoding = params.getByte();
        assert oidEncoding == OID_ENCODING;
        int pos = params.getPosition();
        oid = params.getByteArray(pos, params.size() - pos);
    }

    /**
     * Returns the Object Identification data.
     *
     * @return the oid
     */
    byte[] getOid() {
        byte[] ret = new byte[oid.length];
        System.arraycopy(oid, 0, ret, 0, oid.length);
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ObjectId)) {
            return false;
        }
        ObjectId obj = (ObjectId) o;
        return Arrays.equals(this.oid, obj.oid);
    }

    @Override
    public int hashCode() {
        int ret = 17;
        for (byte b : oid) {
            ret = 31 * ret + b;
        }
        return ret;
    }
}
