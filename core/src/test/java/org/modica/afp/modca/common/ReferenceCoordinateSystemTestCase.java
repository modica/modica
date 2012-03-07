package org.modica.afp.modca.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modica.afp.modca.common.ReferenceCoordinateSystem;

/**
 * Test case for {@link ReferenceCoordinateSystem}.
 */
public class ReferenceCoordinateSystemTestCase {

    @Test
    public void testGetValue() {
        assertEquals(ReferenceCoordinateSystem.INCLUDE_PAGE_SEGMENT,
                ReferenceCoordinateSystem.getValue((byte) 0));
        assertEquals(ReferenceCoordinateSystem.ORIGIN,
                ReferenceCoordinateSystem.getValue((byte) 1));
    }
}
