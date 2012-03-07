package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.RelativeMoveInline;

/**
 * Test case for {@link RelativeMoveInline}.
 */
public class RelativeMoveInlineTestCase extends ControlSequenceTestCase<RelativeMoveInline> {
    private RelativeMoveInline sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { 0x7F, (byte) 0xFF }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.RELATIVE_MOVE_INLINE;
        int length = 4;

        boolean isChained = false;

        sut = new RelativeMoveInline(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x7FFF, sut.getIncrement());

        assertEquals("move 32767", sut.getValueAsString());
    }
}
