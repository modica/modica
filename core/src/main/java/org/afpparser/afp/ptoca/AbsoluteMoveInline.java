package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies a displacement in the I-direction from the B-axis of the object
 * space to a new inline coordinate position, and resumes presentation at the new inline coordinate
 * position. It does not modify the current baseline coordinate position.
 */
public class AbsoluteMoveInline extends ControlSequence {

    private final int displacement;

    public AbsoluteMoveInline(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        displacement = (int) params.getUInt(2);
    }

    /**
     * The amount to displace inline.
     *
     * @return the amount to displace
     */
    public int getDisplacement() {
        return displacement;
    }

    @Override
    public String getValueAsString() {
        return "moveto " + String.valueOf(displacement);
    }
}
