package org.modica.afp.ptoca;

import org.modica.afp.modca.Parameters;

/**
 * The Set Baseline Increment control sequence specifies the increment to be added to the current
 * baseline coordinate when a Begin Line control sequence is executed. This is a modal control
 * sequence.
 */
public class SetBaselineIncrement extends ControlSequence {

    private final int increment;

    public SetBaselineIncrement(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        increment = params.getInt(2);
    }

    /**
     * Specifies an increment in the positive B-direction from the current baseline coordinate
     * position to a new established baseline coordinate position for subsequent presentation text
     * in the current Presentation Text object. The increment is applied when a Begin Line control
     * sequence is executed.
     *
     * @return the baseline increment
     */
    public int getIncrement() {
        return increment;
    }

    @Override
    public String getValueAsString() {
        return "move " + String.valueOf(increment);
    }
}
