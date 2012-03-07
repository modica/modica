package org.modica.afp.ptoca;

/**
 * This control sequence marks the beginning of a new line. It increments the current baseline
 * coordinate position by the amount of the baseline increment. It sets the current inline
 * coordinate to the inline margin. Presentation is resumed at the new baseline coordinate position
 * at the inline margin.
 */
public class BeginLine extends ControlSequence {

    public BeginLine(ControlSequenceIdentifier csId, int length, boolean isChained) {
        super(csId, length, isChained);
    }

    @Override
    public String getValueAsString() {
        return "";
    }
}
