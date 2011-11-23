package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.common.ByteUtils;
import org.afpparser.common.StringUtils;

/**
 * This object is to amalgamate the intelligence of a lot of the {@link End} type structured fields
 * that have names. The name can either match the name or it can match ANY name of a corresponding
 * {@link Begin} structured field or it.
 */
public class EndFieldName {

    private final String name;
    private final boolean nameMatchesAny;

    public EndFieldName(byte[] sfData) throws UnsupportedEncodingException {
        if (ByteUtils.arrayEqualsSubset(sfData, 0xff, 0xff)) {
            name = null;
            nameMatchesAny = true;
        } else {
            name = StringUtils.bytesToCp500(sfData, 0, 8);
            nameMatchesAny = false;
        }
    }

    String getName() {
        return name;
    }

    boolean matchesAny() {
        return nameMatchesAny;
    }

    @Override
    public String toString() {
        return nameMatchesAny ? "matches any" : name;
    }
}
