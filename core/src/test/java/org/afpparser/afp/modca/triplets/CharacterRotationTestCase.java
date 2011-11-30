package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.common.Rotation;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CharacterRotation}
 */
public class CharacterRotationTestCase extends TripletTestCase<CharacterRotation> {
    private CharacterRotation x;
    private final byte[] data = new byte[] { 0x00,  0x2D, 0x5A, (byte) 0x87};


    @Before
    @Override
    public void setUp() {
        byte[] data = new byte[] { 0x00,  0x2D};
        x = new CharacterRotation(data, 0);
        CharacterRotation y = new CharacterRotation(data, 0);
        CharacterRotation z = new CharacterRotation(data, 0);
        CharacterRotation notEqual = new CharacterRotation(data, 1);

        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        CharacterRotation zero = new CharacterRotation(data, 0);
        assertEquals(Rotation.ZERO, zero.getRotation());

        CharacterRotation nintey = new CharacterRotation(data, 1);
        assertEquals(Rotation.NINETY, nintey.getRotation());

        CharacterRotation oneeighty = new CharacterRotation(data, 2);
        assertEquals(Rotation.ONE_EIGHTY, oneeighty.getRotation());

        CharacterRotation twoseventy = new CharacterRotation(data, 3);
        assertEquals(Rotation.TWO_SEVENTY, twoseventy.getRotation());
    }
}
