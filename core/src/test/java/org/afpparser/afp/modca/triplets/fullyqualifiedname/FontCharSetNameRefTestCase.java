package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class FontCharSetNameRefTestCase extends FQNCharStringDataTestCase<FontCharSetNameRef> {
    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        FontCharSetNameRef x = new FontCharSetNameRef(length, expectedString);
        FontCharSetNameRef y = new FontCharSetNameRef(length, expectedString);
        FontCharSetNameRef z = new FontCharSetNameRef(length, expectedString);
        FontCharSetNameRef notEqual = new FontCharSetNameRef(1, "not expected");
        FQNType type = FQNType.font_charset_name_ref;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}
