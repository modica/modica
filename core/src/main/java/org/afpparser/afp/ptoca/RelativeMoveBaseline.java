package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies an increment in the B-direction from the current baseline
 * coordinate position to a new baseline coordinate position. After execution of this control
 * sequence, presentation is resumed at the new baseline coordinate position. A positive value
 * causes movement in the B-direction, while a negative value causes movement toward the I-axis.
 * This control sequence does not modify the current inline coordinate position.
 */
public class RelativeMoveBaseline extends ControlSequence {

    public final int increment;

    public RelativeMoveBaseline(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        increment = params.getInt(2);
    }

    /**
     * The amount to move the baseline.
     *
     * @return the amount to move
     */
    public int getIncrement() {
        return increment;
    }

    @Override
    public String getValueAsString() {
        return "move " + String.valueOf(increment);
    }
}
