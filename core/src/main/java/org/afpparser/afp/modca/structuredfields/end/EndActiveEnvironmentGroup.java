package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.common.StringUtils;

/**
 * The End Active Environment Group structured field terminates the definition of an Active
 * Environment Group initiated by a Begin Active Environment Group structured field.
 */
public class EndActiveEnvironmentGroup extends AbstractStructuredField {

    private final String aegName;
    private final boolean nameMatchesAny;

    public EndActiveEnvironmentGroup(SfIntroducer introducer, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer);
        if (sfData[0] == 0xff && sfData[1] == 0xff) {
            nameMatchesAny = true;
            aegName = null;
        } else {
            aegName = StringUtils.bytesToCp500(sfData);
            nameMatchesAny = false;
        }
    }

    /**
     * Returns the name of the active environment group being terminated. If a name is specified, it
     * must match the name in the most recent Begin Active Environment Group structured field in the
     * page or a X’01’ exception condition exists. If the first two bytes in AEGName contain the
     * value X'FFFF', the name matches any name specified on the Begin Active Environment Group
     * structured field that initiated the current definition.
     *
     * @return the AEG name
     */
    public String getAegName() {
        return aegName;
    }

    /**
     * Returs if the first two bytes in AEGName contain the value X'FFFF', the name matches any name
     * specified on the Begin Active Environment Group structured field that initiated the current
     * definition.
     *
     * @return whether or not the AEG name matches any in the corresponding
     * BeginActiveEnvironmentGroup
     */
    public boolean nameMatchesAny() {
        return nameMatchesAny;
    }

    @Override
    public String toString() {
        return getType().toString() + " AEGName=" + aegName;
    }
}
