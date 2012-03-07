package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.ptoca.BeginLine;
import org.modica.afp.ptoca.ControlSequenceIdentifier;

public class BeginLineTestCase extends ControlSequenceTestCase<BeginLine> {

    private BeginLine sut;

    @Before
    public void setUp() {
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.BEGIN_LINE;
        boolean isChained = false;
        int length = 3;
        sut = new BeginLine(expectedCsId, length, isChained);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals("", sut.getValueAsString());
    }
}
