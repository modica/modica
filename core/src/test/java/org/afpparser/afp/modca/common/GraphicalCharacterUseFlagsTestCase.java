package org.afpparser.afp.modca.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test case for {@link GraphicalCharacterUseFlags}.
 */
public class GraphicalCharacterUseFlagsTestCase {

    @Test
    public void testFlagCheckingMethods() {
        byte flag = 0x07;
        assertTrue(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = 0x01;
        assertTrue(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = 0x02;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = 0x04;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertTrue(GraphicalCharacterUseFlags.isNoIncrement(flag));

        flag = 0x00;
        assertFalse(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoPresentation(flag));
        assertFalse(GraphicalCharacterUseFlags.isNoIncrement(flag));
    }
}
