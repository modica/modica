package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies a displacement in the B-direction from the I-axis of the object
 * space to a new baseline coordinate position. After execution of this control sequence,
 * presentation is resumed at the new baseline coordinate position. This control sequence does not
 * modify the current inline coordinate position.
 */
public class AbsoluteMoveBaseline extends ControlSequence {

    private final int displacement;

    public AbsoluteMoveBaseline(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        displacement = (int) params.getUInt(2);
    }

    /**
     * The amount to displace the baseline.
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
