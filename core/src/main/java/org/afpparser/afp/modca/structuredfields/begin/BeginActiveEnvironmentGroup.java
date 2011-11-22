package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Active Environment Group structured field begins an Active Environment Group, which
 * establishes the environment parameters for the page or overlay. The scope of the active
 * environment group is the containing page or overlay.
 */
public final class BeginActiveEnvironmentGroup extends StructuredFieldWithTriplets {

    private final String aegName;

    public BeginActiveEnvironmentGroup(SfIntroducer introducer, List<Triplet> triplets,
            byte[] sfData) throws UnsupportedEncodingException {
        super(introducer, triplets);
        aegName = StringUtils.bytesToCp500(sfData, 0, 8);
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
}
