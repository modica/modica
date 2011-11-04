package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet contains a GID name reference to a font character set that specifies a set of
 * graphic characters.
 */
public class FontCharSetNameRef extends FQNCharStringData {
    public FontCharSetNameRef(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.font_charset_name_ref;
    }
}
