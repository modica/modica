package org.afpparser.afp.modca.triplets;


/**
 * The Character Rotation triplet is used to specify character rotation relative to the character
 * coordinate system. See the Font Object Content Architecture Reference for further information.
 */
public class CharacterRotation extends Triplet {

    private final static int LENGTH = 4;

    private final Rotation rotation;

    public CharacterRotation(byte[] data, int position) {
        this.rotation = Rotation.getValue(data[position + 1]);
    }

    public enum Rotation {
        ZERO(0x00),
        NINETY(0x2D),
        ONE_EIGHTY(0x5A),
        TWO_SEVENTY(0x87);

        private final byte value;

        private Rotation(int byteValue) {
            this.value = (byte) byteValue;
        }

        public byte[] getBytes() {
            return new byte[] { value, 0x00 };
        }

        private static Rotation getValue(byte identifier) {
            for (Rotation charRotation : Rotation.values()) {
                if (charRotation.value == identifier) {
                    return charRotation;
                }
            }
            throw new IllegalArgumentException(
                    identifier + " is not a valid CharacterRotation value");
        }
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.character_rotation;
    }

    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public String toString() {
        return "character rotation=" + rotation + " degrees";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CharacterRotation)) {
            return false;
        }
        CharacterRotation charRot = (CharacterRotation) o;
        return this.rotation == charRot.rotation;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + rotation.hashCode();
        hashCode = 31 * hashCode + TripletIdentifiers.character_rotation.hashCode();
        return hashCode;
    }

}
