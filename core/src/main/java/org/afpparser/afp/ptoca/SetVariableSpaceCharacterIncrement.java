package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies an increment for the variable space character. The increment is
 * in the I-direction from the presentation position of the variable space character to the
 * addressable position for the next graphic character or control sequence for subsequent text in
 * the current Presentation Text object. This control sequence does not change the current
 * presentation position.
 */
public class SetVariableSpaceCharacterIncrement extends ControlSequence {

    private final int increment;

    public SetVariableSpaceCharacterIncrement(ControlSequenceIdentifier csId, int length,
            boolean isChained, Parameters params) {
        super(csId, length, isChained);
        increment = params.getInt(2);
    }

    /**
     * The inline coordinate of the presentation position is incremented by an increment after each
     * variable space character is processed. Each variable space character causes the presentation
     * position to move in the I-direction by the amount of the variable space character increment.
     * If the value of the increment is zero, no variable space appears between words, and
     * intercharacter adjustment is not applied even though the resulting characters appear
     * side-by-side. When a Set Variable Space Increment control sequence is received, the new value
     * of increment is saved and is applied to any subsequent variable space character received.
     *
     * @return the variable space character increment
     */
    public int getIncrement() {
        return increment;
    }

    @Override
    public String getValueAsString() {
        return "increment=" + increment;
    }
}
