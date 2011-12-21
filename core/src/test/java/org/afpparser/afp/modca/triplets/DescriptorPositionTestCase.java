package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link DescriptorPosition}.
 */
public class DescriptorPositionTestCase extends TripletTestCase<DescriptorPosition> {

    private DescriptorPosition x;
    private final static byte testByte = 0x04;

    @Before
    @Override
    public void setUp() {
        x = new DescriptorPosition(testByte);
        DescriptorPosition y = new DescriptorPosition(testByte);
        DescriptorPosition z = new DescriptorPosition(testByte);
        DescriptorPosition notEqual = new DescriptorPosition((byte) 0x05);

        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(testByte, x.getDesPosId());
    }

    @Test
    @Override
    public void testGetValueAsString() {
        String expectedString = "ObjectAreaPositionId=" + ByteUtils.bytesToHex((byte) 0x04);
        assertEquals(expectedString, x.getValueAsString());
    }
}
