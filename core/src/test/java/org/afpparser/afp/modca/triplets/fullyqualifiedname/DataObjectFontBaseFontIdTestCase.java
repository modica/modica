package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class DataObjectFontBaseFontIdTestCase extends
        FQNCharStringDataTestCase<DataObjectFontBaseFontId> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        DataObjectFontBaseFontId x = new DataObjectFontBaseFontId(length, expectedString);
        DataObjectFontBaseFontId y = new DataObjectFontBaseFontId(length, expectedString);
        DataObjectFontBaseFontId z = new DataObjectFontBaseFontId(length, expectedString);
        DataObjectFontBaseFontId notEqual = new DataObjectFontBaseFontId(1, "not expected");
        FQNType type = FQNType.data_object_font_base_font_id;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}