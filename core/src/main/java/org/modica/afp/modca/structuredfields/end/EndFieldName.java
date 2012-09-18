package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;

import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.modica.afp.modca.structuredfields.SfTypeFactory.End;

/**
 * This object is to amalgamate the intelligence of a lot of the {@link End} type structured fields
 * that have names. The name can either match the name or it can match ANY name of a corresponding
 * {@link Begin} structured field or it.
 */
class EndFieldName {

    private final String name;
    private final boolean nameMatchesAny;

    EndFieldName(Parameters params, int cpgid) throws UnsupportedEncodingException {
        if (params.size() < 2 ||
                (params.getByte() == (byte) 0xFF && params.getByte() == (byte) 0xFF)) {
            name = null;
            nameMatchesAny = true;
        } else {
            name = params.getStringAt(0, 8, cpgid);
            nameMatchesAny = false;
        }
    }

    EndFieldName(Parameters params) throws UnsupportedEncodingException {
        if (params.size() < 2 ||
                (params.getByte() == (byte) 0xFF && params.getByte() == (byte) 0xFF)) {
            name = null;
            nameMatchesAny = true;
        } else {
            name = params.getStringAt(0, 8);
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
