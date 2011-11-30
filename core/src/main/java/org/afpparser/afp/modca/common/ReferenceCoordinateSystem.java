package org.afpparser.afp.modca.common;

/**
 * Specifies the coordinate system and origin used to position the object area
 */
public enum ReferenceCoordinateSystem {
    /**
     * Used only if the object is part of a page segment. The reference coordinate system is the
     * including page or overlay coordinate system. Object areas are positioned in this coordinate
     * system with respect to a point (Xp, Yp) or (Xol, Yol) that is defined by the Include Page
     * Segment (IPS) structured field.
     */
    INCLUDE_PAGE_SEGMENT(0x00),
    /**
     * The reference coordinate system is the including page or overlay coordinate system. Object
     * areas are positioned in this coordinate system with respect to the standard origin defined by
     * (Xp=0, Yp=0) or (Xol=0, Yol=0).
     */
    ORIGIN(0x01);

    private byte id;

    private ReferenceCoordinateSystem(int id) {
        this.id = (byte) id;
    }

    public static ReferenceCoordinateSystem getValue(byte id) {
        for (ReferenceCoordinateSystem refCSys : ReferenceCoordinateSystem.values()) {
            if (refCSys.id == id) {
                return refCSys;
            }
        }
        throw new IllegalArgumentException(id + " is not a valid Reference Co-ordinate System value");
    }

}
