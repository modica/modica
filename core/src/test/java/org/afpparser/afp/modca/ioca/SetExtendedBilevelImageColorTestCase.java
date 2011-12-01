package org.afpparser.afp.modca.ioca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.ioca.SetExtendedBilevelImageColor.ColorSpace;
import org.afpparser.common.ByteUtils;
import org.junit.Test;

/**
 * Test case for {@link SetExtendedBilevelImageColor}.
 */
public class SetExtendedBilevelImageColorTestCase {

    @Test
    public void testConstructor() {
        byte[] data = ByteUtils.createByteArray(11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        SetExtendedBilevelImageColor sut = new SetExtendedBilevelImageColor(data, 0);
        assertEquals(12, sut.getLength());
        assertEquals(ColorSpace.RGB, sut.getColourSpace());
        assertEquals(0x203, sut.getColourSize1());
        assertEquals(0x405, sut.getColourSize2());
        assertEquals(0x607, sut.getColourSize3());
        assertEquals(0x809, sut.getColourSize4());
        assertEquals(0xA0B, sut.getColor());
    }
}
