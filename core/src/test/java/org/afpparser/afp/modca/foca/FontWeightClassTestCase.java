package org.afpparser.afp.modca.foca;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link FontWeightClass}.
 */
public class FontWeightClassTestCase {

    @Test
    public void testGetValue() {
        assertEquals(FontWeightClass.ULTRALIGHT, FontWeightClass.getValue((byte) 1));
        assertEquals(FontWeightClass.EXTRALIGHT, FontWeightClass.getValue((byte) 2));
        assertEquals(FontWeightClass.LIGHT, FontWeightClass.getValue((byte) 3));
        assertEquals(FontWeightClass.SEMILIGHT, FontWeightClass.getValue((byte) 4));
        assertEquals(FontWeightClass.MEDIUM, FontWeightClass.getValue((byte) 5));
        assertEquals(FontWeightClass.SEMIBOLD, FontWeightClass.getValue((byte) 6));
        assertEquals(FontWeightClass.BOLD, FontWeightClass.getValue((byte) 7));
        assertEquals(FontWeightClass.EXTRABOLD, FontWeightClass.getValue((byte) 8));
        assertEquals(FontWeightClass.ULTRABOLD, FontWeightClass.getValue((byte) 9));
    }
}
