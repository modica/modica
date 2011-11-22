package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Presentation Text Object structured field begins a presentation text object which
 * becomes the current data object.
 */
public class BeginPresentationTextObject extends StructuredFieldWithTriplets {

    private final String pTdoName;

    public BeginPresentationTextObject(SfIntroducer introducer, List<Triplet> triplets,
            byte[] sfData) throws UnsupportedEncodingException {
        super(introducer, triplets);
        pTdoName = StringUtils.bytesToCp500(sfData, 0, 8);
    }

    /**
     * Returns the name of the presentation text data object.
     *
     * @return the Presentation Text Object name
     */
    public String getPTdoName() {
        return pTdoName;
    }

    @Override
    public String toString() {
        return getType().toString() + " PTdoName=" + pTdoName;
    }
}
