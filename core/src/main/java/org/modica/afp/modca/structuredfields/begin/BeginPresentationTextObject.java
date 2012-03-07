package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;

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
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("PresentationTextObjectName", pTdoName));
        return params;
    }
}
