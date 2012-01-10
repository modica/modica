package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.NamedColor;

/**
 * The Set Text Color control sequence specifies a color attribute for the foreground areas of the
 * text presentation space.
 */
public class SetTextColor extends ControlSequence {

    private final NamedColor foregroundColour;
    private final boolean precision;

    public SetTextColor(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        foregroundColour = NamedColor.getValue(params);
        if (length > 4) {
            precision = params.getByte() > 0;
        } else {
            precision = false;
        }
        if (precision && foregroundColour == null) {
            throw new IllegalStateException("An invalid colour was given in this PTOCA control" +
                    " sequence.");
        }
    }

    /**
     * Gets the colour that has been specified for the foreground.
     *
     * @return the foreground colour
     */
    public NamedColor getForegroundColour() {
        return foregroundColour;
    }

    @Override
    public String getValueAsString() {
        return foregroundColour.toString();
    }
}
