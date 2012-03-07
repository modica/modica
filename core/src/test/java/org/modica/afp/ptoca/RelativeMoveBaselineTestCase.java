package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.RelativeMoveBaseline;

/**
 * Test case for {@link RelativeMoveBaseline}.
 */
public class RelativeMoveBaselineTestCase extends ControlSequenceTestCase<RelativeMoveBaseline> {
    private RelativeMoveBaseline sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { 0x09, 0x0A }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.RELATIVE_MOVE_BASELINE;
        int length = 4;
        boolean isChained = true;

        sut = new RelativeMoveBaseline(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x90A, sut.getIncrement());

        assertEquals("move 2314", sut.getValueAsString());
    }
}
