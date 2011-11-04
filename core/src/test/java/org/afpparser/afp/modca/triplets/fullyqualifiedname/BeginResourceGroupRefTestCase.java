package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class BeginResourceGroupRefTestCase
        extends FQNCharStringDataTestCase<BeginResourceGroupRef> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        BeginResourceGroupRef x = new BeginResourceGroupRef(length, expectedString);
        BeginResourceGroupRef y = new BeginResourceGroupRef(length, expectedString);
        BeginResourceGroupRef z = new BeginResourceGroupRef(length, expectedString);
        BeginResourceGroupRef notEqual = new BeginResourceGroupRef(1, "not expected");
        FQNType type = FQNType.begin_resource_group_ref;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}