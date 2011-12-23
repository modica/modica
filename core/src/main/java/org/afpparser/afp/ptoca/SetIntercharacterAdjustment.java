package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * The adjustment specifies the value of additional space between graphic characters. This space is
 * in the I-direction from the end of the current character increment to the presentation position
 * of the following graphic character. When this value is positive, the adjustment is referred to as
 * an increment. When the value is negative, the adjustment is referred to as a decrement. The
 * direction specifies the direction in which the intercharacter adjustment is to be applied.
 */
public class SetIntercharacterAdjustment extends ControlSequence {

    private final int adjustment;
    private final boolean directionIsPositive;

    public SetIntercharacterAdjustment(ControlSequenceIdentifier csId, int length,
            boolean isChained, Parameters params) {
        super(csId, length, isChained);
        adjustment = (int) params.getUInt(2);
        if (length > 4) {
            directionIsPositive = params.getByte() == 0;
        } else {
            directionIsPositive = true;
        }
    }

    /**
     * The adjustment specifies the value of additional space between graphic characters. This space
     * is in the I-direction from the end of the current character increment to the presentation
     * position of the following graphic character. When this value is positive, the adjustment is
     * referred to as an increment. When the value is negative, the adjustment is referred to as a
     * decrement.
     *
     * @return the adjustment
     */
    public int getAdjustment() {
        return adjustment;
    }

    /**
     * The direction specifies the direction in which the intercharacter adjustment is to be
     * applied. Intercharacter increment, which occurs when the this returns true, is applied in the
     * positive I-direction. Intercharacter decrement, which occurs when this returns false, is
     * applied in the negative I-direction.
     *
     * @return true if the direction is positive
     */
    public boolean isDirectionPositive() {
        return directionIsPositive;
    }

    @Override
    public String getValueAsString() {
        return "adjustment=" + String.valueOf(adjustment)
                + (directionIsPositive ? "" : " Negative direction");
    }
}
