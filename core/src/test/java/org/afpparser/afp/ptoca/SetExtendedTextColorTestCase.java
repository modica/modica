package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.ColorSpace;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link SetExtendedTextColor}.
 */
public class SetExtendedTextColorTestCase extends ControlSequenceTestCase<SetExtendedTextColor> {
    private SetExtendedTextColor sut;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        byte[] data = ByteUtils.createByteArray(0, 1, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8);
        Parameters params = new Parameters(data, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_EXTENDED_TEXT_COLOR;
        int length = 16;

        boolean isChained = false;

        sut = new SetExtendedTextColor(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(ColorSpace.RGB, sut.getColourSpace());
        assertEquals(1, sut.getColourSize1());
        assertEquals(2, sut.getColourSize2());
        assertEquals(3, sut.getColourSize3());
        assertEquals(4, sut.getColourSize4());
        assertEquals(0x5060708L, sut.getColor());
    }
}
