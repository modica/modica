package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies the dimensions of a rule that extends from the current
 * presentation position in both the B-direction and the I-direction. The current I-axis and B-axis
 * coordinates are not changed by this control sequence.
 */
public class DrawBAxisRule extends ControlSequence {

    private final int length;
    private final double width;

    public DrawBAxisRule(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        this.length = (int) params.getUInt(2);
        if (getLength() > 4) {
            int integerPart = params.getInt(2);
            double fraction = getFraction(params.getByte());
            width = integerPart + fraction;
        } else {
            width = 0;
        }
    }

    /**
     * The length of the rule in the B-direction.
     *
     * @return the length of the rule
     */
    public int getDrawLength() {
        return this.length;
    }

    /**
     * The width of rule in the I-direction.
     *
     * @return the width of the rule
     */
    public double getDrawWidth() {
        return width;
    }

    @Override
    public String getValueAsString() {
        return "length=" + length + " width=" + width;
    }
}
