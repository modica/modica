package org.modica.afp.modca.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modica.afp.modca.common.PresentationSpaceUnits;

/**
 * Test case for {@link PresentationSpaceUnits}.
 */
public class PresentationSpaceUnitsTestCase {

    @Test
    public void testGetValue() {
        assertEquals(PresentationSpaceUnits.INCHES_10, PresentationSpaceUnits.getValue((byte) 0));
        assertEquals(PresentationSpaceUnits.CENTIMETRE_10,
                PresentationSpaceUnits.getValue((byte) 1));
    }
}
