package org.modica.afp.foca;

/**
 * The Weight Class parameter indicates the visual weight (degree or thickness of strokes) of the
 * collection of graphic characters in the font resource. These values are assigned by a font
 * designer, and the visual effect is not defined in FOCA.
 */
public enum FontWeightClass {
    ULTRALIGHT,
    EXTRALIGHT,
    LIGHT,
    SEMILIGHT,
    MEDIUM,
    SEMIBOLD,
    BOLD,
    EXTRABOLD,
    ULTRABOLD;

    private final byte id;

    private FontWeightClass() {
        id = (byte) (this.ordinal() + 1);
    }

    public static FontWeightClass getValue(byte id) {
        for (FontWeightClass weight : FontWeightClass.values()) {
            if (weight.id == id) {
                return weight;
            }
        }
        throw new IllegalArgumentException(id + " is not a valid FontWeightClass");
    }
}
