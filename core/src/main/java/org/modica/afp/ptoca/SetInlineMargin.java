package org.modica.afp.ptoca;

import org.modica.afp.modca.Parameters;

/**
 * This control sequence specifies a displacement from the B-axis in the I-direction that is to be
 * applied when a Begin Line control sequence is processed in the current Presentation Text object.
 * If the value of the displacement is the default indicator, a value is obtained from the
 * hierarchy.
 */
public class SetInlineMargin extends ControlSequence {

    private final int displacement;

    public SetInlineMargin(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        displacement = params.getInt(2);
    }

    /**
     * Returns the displacement inline.
     *
     * @return the displacement
     */
    public int getDisplacement() {
        return displacement;
    }

    @Override
    public String getValueAsString() {
        return "move " + displacement;
    }
}
