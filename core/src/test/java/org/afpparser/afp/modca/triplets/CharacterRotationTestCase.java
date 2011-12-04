package org.afpparser.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
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
        x = new CharacterRotation(new Parameters(data));
        CharacterRotation y = new CharacterRotation(new Parameters(data));
        CharacterRotation z = new CharacterRotation(new Parameters(data));
        Parameters params = new Parameters(data);
        params.skip(1);
        CharacterRotation notEqual = new CharacterRotation(params);

        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetterMethods() {
        CharacterRotation zero = new CharacterRotation(new Parameters(data));
        assertEquals(Rotation.ZERO, zero.getRotation());

        Parameters params1 = new Parameters(data);
        params1.skip(1);
        CharacterRotation nintey = new CharacterRotation(params1);
        assertEquals(Rotation.NINETY, nintey.getRotation());

        Parameters params2 = new Parameters(data);
        params2.skip(2);
        CharacterRotation oneeighty = new CharacterRotation(params2);
        assertEquals(Rotation.ONE_EIGHTY, oneeighty.getRotation());

        Parameters params3 = new Parameters(data);
        params3.skip(3);
        CharacterRotation twoseventy = new CharacterRotation(params3);
        assertEquals(Rotation.TWO_SEVENTY, twoseventy.getRotation());
    }
}
