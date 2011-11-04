package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class BeginPageGroupRefTestCase  extends FQNCharStringDataTestCase<BeginPageGroupRef> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        BeginPageGroupRef x = new BeginPageGroupRef(length, expectedString);
        BeginPageGroupRef y = new BeginPageGroupRef(length, expectedString);
        BeginPageGroupRef z = new BeginPageGroupRef(length, expectedString);
        BeginPageGroupRef notEqual = new BeginPageGroupRef(1, "not expected");
        FQNType type = FQNType.begin_page_group_ref;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}
