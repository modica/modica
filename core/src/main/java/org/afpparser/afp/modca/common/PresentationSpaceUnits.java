package org.afpparser.afp.modca.common;


/**
 * An enumerated type that describes the base units for the page or the overlay coordinate
 * system.
 */
public enum PresentationSpaceUnits {
    /** Units measured in units of 10 inches */
    INCHES_10(0x00),
    /** Units measured in units of 10 centimetres */
    CENTIMETRE_10(0x01);

    private final byte id;

    private PresentationSpaceUnits(int id) {
        this.id = (byte) id;
    }

    public static PresentationSpaceUnits getValue(byte id) {
        for (PresentationSpaceUnits unit : PresentationSpaceUnits.values()) {
            if (unit.id == id) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Byte value:" + id + " is not a valid PageUnit");
    }
}