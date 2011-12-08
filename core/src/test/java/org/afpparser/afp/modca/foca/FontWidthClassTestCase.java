package org.afpparser.afp.modca.foca;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link FontWidthClass}
 */
public class FontWidthClassTestCase {

    @Test
    public void testGetValue() {
        assertEquals(FontWidthClass.ULTRACONDENSED, FontWidthClass.getValue((byte) 1));
        assertEquals(FontWidthClass.EXTRACONDENSED, FontWidthClass.getValue((byte) 2));
        assertEquals(FontWidthClass.CONDENSED, FontWidthClass.getValue((byte) 3));
        assertEquals(FontWidthClass.SEMICONDENSED, FontWidthClass.getValue((byte) 4));
        assertEquals(FontWidthClass.MEDIUM, FontWidthClass.getValue((byte) 5));
        assertEquals(FontWidthClass.SEMIEXPANDED, FontWidthClass.getValue((byte) 6));
        assertEquals(FontWidthClass.EXPANDED, FontWidthClass.getValue((byte) 7));
        assertEquals(FontWidthClass.EXTRAEXPANDED, FontWidthClass.getValue((byte) 8));
        assertEquals(FontWidthClass.ULTRAEXPANDED, FontWidthClass.getValue((byte) 9));
    }
}
