package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * This GID replaces the first parameter in the structured field that contains a GID name.
 */
public class ReplaceFirstGidName extends FQNCharStringData {

    ReplaceFirstGidName(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.replace_first_gid_name;
    }
}
