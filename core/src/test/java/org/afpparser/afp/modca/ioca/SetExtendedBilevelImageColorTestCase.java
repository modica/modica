package org.afpparser.afp.modca.ioca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.ioca.SetExtendedBilevelImageColor.ColorSpace;
import org.afpparser.common.ByteUtils;
import org.junit.Test;

/**
 * Test case for {@link SetExtendedBilevelImageColor}.
 */
public class SetExtendedBilevelImageColorTestCase {

    @Test
    public void testConstructor() {
        Parameters params = new Parameters(ByteUtils.createByteArray(11,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), "Cp500");
        SetExtendedBilevelImageColor sut = new SetExtendedBilevelImageColor(params);
        assertEquals(12, sut.getLength());
        assertEquals(ColorSpace.RGB, sut.getColourSpace());
        assertEquals(0x203, sut.getColourSize1());
        assertEquals(0x405, sut.getColourSize2());
        assertEquals(0x607, sut.getColourSize3());
        assertEquals(0x809, sut.getColourSize4());
        assertEquals(0xA0B, sut.getColor());

        String expectedString = "ColourSpace=" + ColorSpace.RGB.toString()
                + " colSize1=" + 0x203
                + " colSize2=" + 0x405
                + " colSize3=" + 0x607
                + " colSize4=" + 0x809;
        assertEquals(expectedString, sut.getValueAsString());
    }
}
