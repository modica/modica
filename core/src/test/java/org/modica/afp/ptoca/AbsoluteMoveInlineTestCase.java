package org.modica.afp.ptoca;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;

import static org.junit.Assert.assertEquals;

public class AbsoluteMoveInlineTestCase extends ControlSequenceTestCase<AbsoluteMoveInline> {
    private AbsoluteMoveInline sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] {0x7F, (byte) 0xFF});
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.ABSOLUTE_MOVE_INLINE;
        int length = 4;
        boolean isChained = true;

        sut = new AbsoluteMoveInline(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x7FFF, sut.getDisplacement());

        assertEquals("moveto 32767", sut.getValueAsString());
    }
}
