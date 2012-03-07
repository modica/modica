package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.NamedColor;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.SetTextColor;

/**
 * Test case for {@link SetTextColor}.
 */
public class SetTextColorTestCase extends ControlSequenceTestCase<SetTextColor> {

    private SetTextColor sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { 0, 0x02, 1 }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_TEXT_COLOR;
        int length = 4;
        boolean isChained = true;

        sut = new SetTextColor(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);

        length = 5;
        params = new Parameters(new byte[] { 0, 0x33, 1 }, "Cp500");
        try {
            new SetTextColor(expectedCsId, length, isChained, params);
            fail("Precision has been set with an invalid colour, an exception is expected");
        } catch (IllegalStateException ise) {
            //Pass
        }
    }

    @Test
    public void testGetterMethods() {
        assertEquals(NamedColor.RED, sut.getForegroundColour());
        assertEquals(NamedColor.RED.toString(), sut.getValueAsString());

        assertEquals(NamedColor.RED, sut.getForegroundColour());
        assertEquals(NamedColor.RED.toString(), sut.getValueAsString());
    }
}