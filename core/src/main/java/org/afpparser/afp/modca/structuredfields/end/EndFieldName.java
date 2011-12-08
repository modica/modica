package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;

/**
 * This object is to amalgamate the intelligence of a lot of the {@link End} type structured fields
 * that have names. The name can either match the name or it can match ANY name of a corresponding
 * {@link Begin} structured field or it.
 */
public class EndFieldName {

    private final String name;
    private final boolean nameMatchesAny;

    public EndFieldName(Parameters params, String encoding) throws UnsupportedEncodingException {
        if (params.size() < 2 ||
                (params.getByte() == (byte) 0xFF && params.getByte() == (byte) 0xFF)) {
            name = null;
            nameMatchesAny = true;
        } else {
            name = params.getString(0, 8, encoding);
            nameMatchesAny = false;
        }
    }

    public EndFieldName(Parameters params) throws UnsupportedEncodingException {
        if (params.size() < 2 ||
                (params.getByte() == (byte) 0xFF && params.getByte() == (byte) 0xFF)) {
            name = null;
            nameMatchesAny = true;
        } else {
            name = params.getString(0, 8);
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
