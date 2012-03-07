package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.Overstrike;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link Overstrike}.
 */
public class OverstrikeTestCase extends ControlSequenceTestCase<Overstrike> {
    private Overstrike sut;
    private final byte[] data = ByteUtils.createByteArray(0x08, 1, 2);

    @Before
    public void setUp() throws UnsupportedEncodingException {
        Parameters params = new Parameters(data, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.OVERSTRIKE;
        int length = 5;
        boolean isChained = false;

        sut = new Overstrike(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertTrue(sut.bypassRelativeMoveInline());
        assertFalse(sut.bypassAbsoluteMoveInline());
        assertFalse(sut.bypassSpaceChars());
        assertEquals(0x102, sut.getOverstrikeCharacter());

        String expectedString = "BypassRMI=true BypassAMI=false BypassSpaceChars=false";
        assertEquals(expectedString, sut.getValueAsString());
    }
}
