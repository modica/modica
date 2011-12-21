package org.afpparser.afp.ptoca;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.common.ByteUtils;

/**
 * This control sequence specifies a local identifier, LID, which is used by the font resource to
 * access a coded font for presentation of subsequent text in the current Presentation Text object.
 * The current presentation position is not changed by this control sequence.
 */
public class SetCodedFontLocal extends ControlSequence {

    private final byte lid;

    public SetCodedFontLocal(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        lid = params.getByte();
    }

    /**
     * The LID is equated to a Graphic Character Set Global Identifier (GCSGID), Code Page Global
     * Identifier (CPGID), Font Global Identifier (FGID), character rotation, and font modification
     * parameters by a mapping function in the controlling environment.
     *
     * @return the local identifier
     */
    public byte getLocalId() {
        return lid;
    }

    @Override
    public String getValueAsString() {
        return "lid=" + ByteUtils.bytesToHex(lid);
    }
}
