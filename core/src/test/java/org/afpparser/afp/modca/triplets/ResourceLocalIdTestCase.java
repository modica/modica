package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.triplets.ResourceLocalId.ResourceType;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link ResourceLocalId}.
 */
public class ResourceLocalIdTestCase extends TripletTestCase<ResourceLocalId> {
    private ResourceLocalId x;

    @Before
    @Override
    public void setUp() {
        byte[] data = new byte[] { 0x00, 0x02, 0x03 };
        x = new ResourceLocalId(data, 0);
        ResourceLocalId y = new ResourceLocalId(data, 0);
        ResourceLocalId z = new ResourceLocalId(data, 0);
        ResourceLocalId notEqual = new ResourceLocalId(data, 1);

        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        testResourceType((byte) 0x00, ResourceType.USAGE_DEPENDENT);
        testResourceType((byte) 0x02, ResourceType.PAGE_OVERLAY);
        testResourceType((byte) 0x05, ResourceType.CODED_FONT);
        testResourceType((byte) 0x07, ResourceType.COLOR_ATTRIBUTE_TABLE);
    }

    private void testResourceType(byte typeId, ResourceType type) {
        byte[] data = new byte[] { typeId, 0x01 };
        ResourceLocalId testSubject = new ResourceLocalId(data, 0);
        assertEquals(type, testSubject.getResourceType());
    }
}
