package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class CodePageNameRefTestCase extends FQNCharStringDataTestCase<CodePageNameRef> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        CodePageNameRef x = new CodePageNameRef(length, expectedString);
        CodePageNameRef y = new CodePageNameRef(length, expectedString);
        CodePageNameRef z = new CodePageNameRef(length, expectedString);
        CodePageNameRef notEqual = new CodePageNameRef(1, "not expected");
        FQNType type = FQNType.code_page_name_ref;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}