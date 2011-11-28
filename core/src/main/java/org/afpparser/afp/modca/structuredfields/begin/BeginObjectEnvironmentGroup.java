package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Object Environment Group structured field begins an Object Environment Group, which
 * establishes the environment parameters for the object. The scope of an object environment group
 * is its containing object.
 */
public class BeginObjectEnvironmentGroup extends StructuredFieldWithTriplets {

    private final String oegName;

    public BeginObjectEnvironmentGroup(SfIntroducer introducer, List<Triplet> triplets,
            byte[] sfData) throws UnsupportedEncodingException {
        super(introducer, triplets);
        oegName = StringUtils.bytesToCp500(sfData, 0, 8);
    }

    /**
     * Returns the name of the object environment group.
     *
     * @return the OEG name
     */
    public String getOegName() {
        return oegName;
    }

    @Override
    public String toString() {
        return getType().toString() + " OEGName=" + oegName;
    }
}
