package org.modica.afp.ptoca;

import org.modica.afp.modca.Parameters;
import org.modica.common.ByteUtils;

/**
 * This control sequence marks the end of a string of presentation text that has been suppressed.
 * It works in conjunction with the Begin Suppression control sequence. If the value of the LID is
 * not supported or is not within the range specified by PTOCA, exception condition EC-9801 exists.
 * The standard action in this case is to ignore this control sequence and continue presentation
 * with the value determined according to the data-stream hierarchy.
 */
public class EndSuppression extends ControlSequence {

    private final byte lid;

    public EndSuppression(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        lid = params.getByte();
    }

    /**
     * The local identifier, is used to match with a corresponding BeginSuppression control
     * sequence.
     *
     * @return the local identifier
     */
    public byte getLid() {
        return lid;
    }

    @Override
    public String getValueAsString() {
        return "lid=0x" + ByteUtils.bytesToHex(lid);
    }
}
