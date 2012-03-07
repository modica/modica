package org.modica.afp.ptoca;

import org.modica.afp.modca.Parameters;

/**
 * The Underscore control sequence identifies text fields that are to be underscored.
 */
public class Underscore extends ControlSequence {

    private final BypassFlags bypassFlags;

    public Underscore(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        bypassFlags = new BypassFlags(params.getByte());
    }

    /**
     * Returning false indicates that the controlled white space generated as a result of a Relative
     * Move Inline control sequence is to be underscored. Returning true indicates that such
     * controlled white space is not to be underscored. It should be bypassed.
     *
     * @return whether to bypass a relative move inline
     */
    public boolean bypassRelativeMoveInline() {
        return bypassFlags.bypassRelativeMoveInline();
    }

    /**
     * Returning false indicates that the controlled white space generated as a result of an
     * Absolute Move Inline control sequence is to be underscored. Returning true indicates that
     * such controlled white space is not to be underscored. It should be bypassed.
     *
     * @return whether to bypass an absolute move inline
     */
    public boolean bypassAbsoluteMoveInline() {
        return bypassFlags.bypassAbsoluteMoveInline();
    }

    /**
     * Returning false indicates that the controlled white space generated as a result of space
     * characters or variable space characters is to be underscored. Returning true indicates that
     * such controlled white space is not to be underscored. It should be bypassed.
     *
     * @return whether to bypass space characters
     */
    public boolean bypassSpaceChars() {
        return bypassFlags.bypassSpaceChars();
    }

    @Override
    public String getValueAsString() {
        return "BypassRMI=" + bypassRelativeMoveInline()
                + " BypassAMI=" + bypassAbsoluteMoveInline()
                + " BypassSpaceChars=" + bypassSpaceChars();
    }
}
