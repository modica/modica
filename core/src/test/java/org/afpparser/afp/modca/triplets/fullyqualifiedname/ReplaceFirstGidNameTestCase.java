package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class ReplaceFirstGidNameTestCase extends FQNCharStringDataTestCase<ReplaceFirstGidName> {
    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        ReplaceFirstGidName x = new ReplaceFirstGidName(length, expectedString);
        ReplaceFirstGidName y = new ReplaceFirstGidName(length, expectedString);
        ReplaceFirstGidName z = new ReplaceFirstGidName(length, expectedString);
        ReplaceFirstGidName notEqual = new ReplaceFirstGidName(1, "not expected");
        FQNType type = FQNType.replace_first_gid_name;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}
