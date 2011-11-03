package org.afpparser.afp.modca.triplets.fullyqualifiedname;

public class CodePageNameRef extends FQNCharStringData {
    public CodePageNameRef(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.code_page_name_ref;
    }
}

