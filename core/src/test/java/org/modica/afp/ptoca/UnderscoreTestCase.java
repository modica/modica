package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.Underscore;
import org.modica.common.ByteUtils;

public class UnderscoreTestCase extends ControlSequenceTestCase<Underscore> {

    private Underscore sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(ByteUtils.createByteArray(0));
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.UNDERSCORE;
        int length = 3;
        boolean isChained = true;

        sut = new Underscore(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
}

    @Test
    public void testGetterMethods() {
        assertEquals(false, sut.bypassRelativeMoveInline());
        assertEquals(false, sut.bypassAbsoluteMoveInline());
        assertEquals(false, sut.bypassSpaceChars());
        String expected = "BypassRMI=" + false
                + " BypassAMI=" + false
                + " BypassSpaceChars=" + false;
        assertEquals(expected, sut.getValueAsString());
    }
}