package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import java.net.URL;

public class FQNUrlData extends FullyQualifiedName {
    private final URL data;
    private final FQNType fqnType;

    FQNUrlData(int length, URL data, FQNType fqnType) {
        super(length);
        this.data = data;
        this.fqnType = fqnType;
    }

    /**
     * Returns the URL data.
     *
     * @return the URL
     */
    public URL getString() {
        return this.data;
    }

    @Override
    public FQNFmt getFormat() {
        return FQNFmt.url;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FQNUrlData)) {
            return false;
        }
        FQNUrlData fqn = (FQNUrlData) o;
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
        return getTid().name() + ", " + getFQNType() + "=" + data;
    }

    @Override
    public FQNType getFQNType() {
        return fqnType;
    }

    @Override
    public String getValueAsString() {
        return "URL=" + data.toString();
    }
}
