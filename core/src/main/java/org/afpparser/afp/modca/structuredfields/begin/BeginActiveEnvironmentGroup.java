package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.AbstractStructuredField;
import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Active Environment Group structured field begins an Active Environment Group, which
 * establishes the environment parameters for the page or overlay. The scope of the active
 * environment group is the containing page or overlay.
 */
public class BeginActiveEnvironmentGroup extends AbstractStructuredField {

    private final String aegName;

    public BeginActiveEnvironmentGroup(SfIntroducer introducer, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer);
        aegName = StringUtils.bytesToCp500(sfData);
    }

    /**
     * Is the name of the active environment group.
     *
     * @return the AEG name
     */
    public String getAegName() {
        return aegName;
    }

    @Override
    public String toString() {
        return getType().getName() + " aeg-name=" + aegName;
    }

    @Override
    public boolean hasTriplets() {
        return false;
    }

    @Override
    public List<Triplet> getTriplets() {
        return null;
    }
}
