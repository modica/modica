package org.afpparser.afp.modca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

public class StructuredFieldTestCase {
    private StructuredField sut;
    private StructuredFieldType sutType;

    @Before
    public void setUp() {
        byte[] typeId = ByteUtils.createByteArray(0xD3, 0xA0, 0x88);
        sutType = StructuredFieldType.getValue(typeId);
        sut = new StructuredField(1L, 2, typeId, (byte) 3, 5);
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
            StructuredField x = createField();
            StructuredField y = createField();
            StructuredField z = createField();

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
        StructuredField subject = createField();
        StructuredField offsetNotEqual = new StructuredField(2L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x88), (byte) 3, 5);
        checkNotEquals(subject, offsetNotEqual);

        StructuredField lengthNotEquals = new StructuredField(1L, 1, ByteUtils.createByteArray(
                0xD3, 0xA0, 0x88), (byte) 3, 5);
        checkNotEquals(subject, lengthNotEquals);

        StructuredField typeNotEquals = new StructuredField(1L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x90), (byte) 3, 5);
        checkNotEquals(subject, typeNotEquals);

        StructuredField flagsNotEquals = new StructuredField(1L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x88), (byte) 4, 5);
        checkNotEquals(subject, flagsNotEquals);

        StructuredField extLengthNotEquals = new StructuredField(1L, 2, ByteUtils.createByteArray(
                0xD3, 0xA0, 0x88), (byte) 3, 6);
        checkNotEquals(subject, extLengthNotEquals);
    }

    private void checkNotEquals(StructuredField o1, StructuredField o2) {
        assertFalse(o1.equals(o2));
        assertNotSame(o1.hashCode(), o2.hashCode());
    }

    private StructuredField createField() {
        return new StructuredField(1L, 2, ByteUtils.createByteArray(0xD3, 0xA0, 0x88), (byte) 3, 5);
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
        StructuredField field = new StructuredField(0, 1, StructuredFieldType.BCF.getId(),
                (byte) flag, 0);
        assertEquals(hasExtData, field.hasExtData());
        assertEquals(hasSegData, field.hasSegmentedData());
        assertEquals(hasDataPadding, field.hasDataPadding());
    }
}
