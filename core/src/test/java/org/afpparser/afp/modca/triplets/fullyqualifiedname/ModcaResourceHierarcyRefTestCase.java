package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class ModcaResourceHierarcyRefTestCase
        extends FQNCharStringDataTestCase<ModcaResourceHierarchyRef> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        ModcaResourceHierarchyRef x = new ModcaResourceHierarchyRef(length, expectedString);
        ModcaResourceHierarchyRef y = new ModcaResourceHierarchyRef(length, expectedString);
        ModcaResourceHierarchyRef z = new ModcaResourceHierarchyRef(length, expectedString);
        ModcaResourceHierarchyRef notEqual = new ModcaResourceHierarchyRef(1, "not expected");
        FQNType type = FQNType.modca_resource_hierarchy_ref;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}
