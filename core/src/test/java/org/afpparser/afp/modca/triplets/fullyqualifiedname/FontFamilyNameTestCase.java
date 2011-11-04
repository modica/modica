package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class FontFamilyNameTestCase extends FQNCharStringDataTestCase<FontFamilyName> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        FontFamilyName x = new FontFamilyName(length, expectedString);
        FontFamilyName y = new FontFamilyName(length, expectedString);
        FontFamilyName z = new FontFamilyName(length, expectedString);
        FontFamilyName notEqual = new FontFamilyName(1, "not expected");
        FQNType type = FQNType.font_family_name;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}
