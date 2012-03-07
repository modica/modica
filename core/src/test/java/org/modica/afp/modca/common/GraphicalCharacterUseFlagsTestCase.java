package org.modica.afp.modca.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.modica.afp.modca.common.GraphicalCharacterUseFlags;

/**
 * Test case for {@link GraphicalCharacterUseFlags}.
 */
public class GraphicalCharacterUseFlagsTestCase {

    @Test
    public void testFlagCheckingMethods() {
        byte flag = (byte) 0xE0;
        assertTrue(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = (byte) 0x80;
        assertTrue(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = (byte) 0x40;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = (byte) 0x20;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = (byte) 0x00;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));
    }
}
