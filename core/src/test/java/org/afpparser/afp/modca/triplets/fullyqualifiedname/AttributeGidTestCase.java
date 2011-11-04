package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class AttributeGidTestCase extends FQNCharStringDataTestCase<AttributeGid> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        AttributeGid x = new AttributeGid(length, expectedString);
        AttributeGid y = new AttributeGid(length, expectedString);
        AttributeGid z = new AttributeGid(length, expectedString);
        AttributeGid notEqual = new AttributeGid(1, "not expected");
        FQNType type = FQNType.attribute_gid;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}
