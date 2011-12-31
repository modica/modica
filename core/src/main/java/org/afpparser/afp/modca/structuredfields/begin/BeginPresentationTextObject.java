package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The Begin Presentation Text Object structured field begins a presentation text object which
 * becomes the current data object.
 */
public class BeginPresentationTextObject extends StructuredFieldWithTriplets {

    private final String pTdoName;

    public BeginPresentationTextObject(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params) throws UnsupportedEncodingException {
        super(introducer, triplets);
        pTdoName = params.getString(0, 8);
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

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("PresentationTextObjectName", pTdoName);
        return params;
    }
}
