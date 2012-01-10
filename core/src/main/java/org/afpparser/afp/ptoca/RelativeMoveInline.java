package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies an increment in the I-direction from the current inline
 * coordinate position to a new inline coordinate position. After execution of this control
 * sequence, presentation is resumed at the new inline coordinate position. A positive value is in
 * the direction of line growth, while a negative value logically backspaces. This control sequence
 * does not modify the current baseline coordinate position.
 */
public class RelativeMoveInline extends ControlSequence {

    public final int increment;

    public RelativeMoveInline(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        increment = params.getInt(2);
    }

    /**
     * The amount to move inline.
     *
     * @return the amount to move
     */
    public int getIncrement() {
        return increment;
    }

    @Override
    public String getValueAsString() {
        return "move " + increment;
    }
}
