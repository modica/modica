package org.modica.afp.modca.common;

/**
 * Specifies the clockwise character rotation relative to the character coordinate system.
 */
public enum Rotation {
    ZERO(0x00),
    NINETY(0x2D),
    ONE_EIGHTY(0x5A),
    TWO_SEVENTY(0x87);

    private final byte value;

    private Rotation(int byteValue) {
        this.value = (byte) byteValue;
    }

    /**
     * Get the identifying byte array.
     *
     * @return the bytes identifying this character rotation
     */
    public byte[] getBytes() {
        return new byte[] { value, 0x00 };
    }

    /**
     * Returns the character rotation as an enumerated type.
     *
     * @param identifier the byte value
     * @return an enumeration of character rotation
     */
    public static Rotation getValue(byte identifier) {
        for (Rotation charRotation : Rotation.values()) {
            if (charRotation.value == identifier) {
                return charRotation;
            }
        }
        throw new IllegalArgumentException(
                identifier + " is not a valid CharacterRotation value");
    }
}
