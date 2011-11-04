package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class ColorManagementResourceTestCase extends
        FQNCharStringDataTestCase<ColorManagementResource> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        ColorManagementResource x = new ColorManagementResource(length, expectedString);
        ColorManagementResource y = new ColorManagementResource(length, expectedString);
        ColorManagementResource z = new ColorManagementResource(length, expectedString);
        ColorManagementResource notEqual = new ColorManagementResource(1, "not expected");
        FQNType type = FQNType.color_management_resource_ref;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}