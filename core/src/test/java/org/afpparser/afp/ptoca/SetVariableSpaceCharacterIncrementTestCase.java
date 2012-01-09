package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

public class SetVariableSpaceCharacterIncrementTestCase
        extends ControlSequenceTestCase<SetVariableSpaceCharacterIncrement> {

    private SetVariableSpaceCharacterIncrement sut;

    @Before
    public void setUp() {
        Parameters params = new Parameters(ByteUtils.createByteArray(1, 2), "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_VARIABLE_SPACE_CHARACTER_INCREMENT;
        int length = 4;
        boolean isChained = true;

        sut = new SetVariableSpaceCharacterIncrement(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, sut.getIncrement());
        assertEquals("increment=258", sut.getValueAsString());
    }
}
