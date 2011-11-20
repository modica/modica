package org.afpparser.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfType;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Attribute;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

public class SfIntroducerTestCase {
    private SfIntroducer sut;
    private SfType sutType;

    @Before
    public void setUp() {
        byte[] typeId = ByteUtils.createByteArray(0xD3, 0xAE, 0x89);
        sutType = SfTypeFactory.getValue(typeId);
        sut = new SfIntroducer(1L, 2, typeId, (byte) 3, 5);
    }

    @Test
    public void testGetters() {
        assertEquals(1L, sut.getOffset());
        assertEquals(2, sut.getLength());
        assertEquals(sutType, sut.getType());
        assertTrue(sut.hasExtData());
        assertEquals(5, sut.getExtLength());
    }

    @Test
    public void testHashCodeEquals() {
        // Consistency
        for (int i = 0; i < 100; i++) {
            SfIntroducer x = createGenericIntroducer();
            SfIntroducer y = createGenericIntroducer();
            SfIntroducer z = createGenericIntroducer();

            // Reflectivity
            assertTrue(x.equals(x));
            assertTrue(y.equals(y));
            assertTrue(z.equals(z));

            // Symmetry
            assertTrue(x.equals(y));
            assertTrue(y.equals(z));

            // Transitivity
            assertTrue(x.equals(y));
            assertTrue(y.equals(z));
            assertTrue(x.equals(z));

            // hashCode
            assertEquals(x.hashCode(), y.hashCode());
            assertEquals(y.hashCode(), z.hashCode());
        }
    }

    @Test
    public void testEqualsHashCodeFailureCases() {
        SfIntroducer subject = createGenericIntroducer();
        SfIntroducer offsetNotEqual = new SfIntroducer(2L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x88), (byte) 3, 5);
        checkNotEquals(subject, offsetNotEqual);

        SfIntroducer lengthNotEquals = new SfIntroducer(1L, 1, ByteUtils.createByteArray(
                0xD3, 0xA0, 0x88), (byte) 3, 5);
        checkNotEquals(subject, lengthNotEquals);

        SfIntroducer typeNotEquals = new SfIntroducer(1L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x90), (byte) 3, 5);
        checkNotEquals(subject, typeNotEquals);

        SfIntroducer flagsNotEquals = new SfIntroducer(1L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x88), (byte) 4, 5);
        checkNotEquals(subject, flagsNotEquals);

        SfIntroducer extLengthNotEquals = new SfIntroducer(1L, 2, ByteUtils.createByteArray(
                0xD3, 0xA0, 0x88), (byte) 3, 6);
        checkNotEquals(subject, extLengthNotEquals);
    }

    private void checkNotEquals(SfIntroducer o1, SfIntroducer o2) {
        assertFalse(o1.equals(o2));
        assertNotSame(o1.hashCode(), o2.hashCode());
    }

    /**
     * Creates an introducer object, populated with arbitrary values for testing against.
     *
     * @return the introducer
     */
    public static SfIntroducer createGenericIntroducer() {
        return createGenericIntroducer(Attribute.MFC);
    }

    /**
     * Creates an introducer object of SfType type, populated with arbitrary values for testing.
     *
     * @param type the SfType
     * @return the introducer
     */
    public static SfIntroducer createGenericIntroducer(SfType type) {
        byte[] bytes = ByteUtils.createByteArray(
                0xD3,
                type.getTypeCode().getValue() & 0xFF,
                type.getCategoryCode().getValue() & 0xFF);
        return new SfIntroducer(1L, 2, bytes, (byte) 3, 5);
    }

    @Test
    public void testFlags() {
        for (int i = 0; i < 8; i++) {
            boolean hasExtData = (i & 1) > 0;
            boolean hasSegData = (i & 4) > 0;
            boolean hasDataPadding = (i & 16) > 0;
            testSFWithFlag(i, hasExtData, hasSegData, hasDataPadding);
        }
    }

    private void testSFWithFlag(int flag, boolean hasExtData, boolean hasSegData,
            boolean hasDataPadding) {
        byte[] id = ByteUtils.createByteArray(0xD3, 0xA0, 0x88);
        SfIntroducer field = new SfIntroducer(0, 1, id, (byte) flag, 0);
        assertEquals(hasExtData, field.hasExtData());
        assertEquals(hasSegData, field.hasSegmentedData());
        assertEquals(hasDataPadding, field.hasDataPadding());
    }
}
