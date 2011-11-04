package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class ProcessElementGidTestCase extends FQNCharStringDataTestCase<ProcessElementGid> {
    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        ProcessElementGid x = new ProcessElementGid(length, expectedString);
        ProcessElementGid y = new ProcessElementGid(length, expectedString);
        ProcessElementGid z = new ProcessElementGid(length, expectedString);
        ProcessElementGid notEqual = new ProcessElementGid(1, "not expected");
        FQNType type = FQNType.process_element_gid;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}
