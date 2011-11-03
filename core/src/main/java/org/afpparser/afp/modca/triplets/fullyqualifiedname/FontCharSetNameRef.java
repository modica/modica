package org.afpparser.afp.modca.triplets.fullyqualifiedname;

public class FontCharSetNameRef extends FQNCharStringData {
    public FontCharSetNameRef(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.font_charset_name_ref;
    }
}
