package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.SetBaselineIncrement;

/**
 * Test case for {@link SetBaselineIncrement}.
 */
public class SetBaselineIncrementTestCase extends ControlSequenceTestCase<SetBaselineIncrement> {
    private SetBaselineIncrement sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { 0x7F, (byte) 0xFF }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_BASELINE_INCREMENT;
        int length = 4;
        boolean isChained = true;

        sut = new SetBaselineIncrement(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x7FFF, sut.getIncrement());

        assertEquals("move 32767", sut.getValueAsString());
    }
}
