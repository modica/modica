package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.afpparser.afp.modca.Parameters;

/**
 * The global resource identifier (GRID) is an eight-byte binary identifier used to reference
 * a coded font.
 */
public final class GlobalResourceId {
    private final int gcsgid;
    private final int cpgid;
    private final int fgid;
    private final int fontWidth;

    GlobalResourceId(Parameters params) {
        gcsgid = (int) params.getUInt(2);
        cpgid = (int) params.getUInt(2);
        fgid = (int) params.getUInt(2);
        fontWidth = (int) params.getUInt(2);
    }

    public int getGcsgid() {
        return gcsgid;
    }

    public int getCpgid() {
        return cpgid;
    }

    public int getFgid() {
        return fgid;
    }

    public int getFontWidth() {
        return fontWidth;
    }

    @Override
    public int hashCode() {
        int ret = 17;
        ret = 31 * ret + gcsgid;
        ret = 31 * ret + cpgid;
        ret = 31 * ret + fgid;
        ret = 31 * ret + fontWidth;
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GlobalResourceId)) {
            return false;
        }
        GlobalResourceId obj = (GlobalResourceId) o;
        return this.gcsgid == obj.gcsgid
                && this.cpgid == obj.cpgid
                && this.fgid == obj.fgid
                && this.fontWidth == obj.fontWidth;
    }

    @Override
    public String toString() {
        return "GCSGID=" + String.valueOf(gcsgid)
                + " CPGID=" + String.valueOf(cpgid)
                + " FGID=" + String.valueOf(fgid)
                + " FontWidth=" + String.valueOf(fontWidth);
    }
}
