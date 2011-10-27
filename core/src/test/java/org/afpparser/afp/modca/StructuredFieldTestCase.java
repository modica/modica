package org.afpparser.afp.modca;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StructuredFieldTestCase {
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
