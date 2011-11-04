package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class FontTypefaceNameTestCase extends FQNCharStringDataTestCase<FontTypefaceName> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        FontTypefaceName x = new FontTypefaceName(length, expectedString);
        FontTypefaceName y = new FontTypefaceName(length, expectedString);
        FontTypefaceName z = new FontTypefaceName(length, expectedString);
        FontTypefaceName notEqual = new FontTypefaceName(1, "not expected");
        FQNType type = FQNType.font_typeface_name;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}