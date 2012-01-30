package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The Begin Image Object structured field begins an IOCA image data object, which becomes the
 * current data object.
 * <p>
 * Architecture Note: A migration form of the image object is supported in AFP environments and is
 * defined as the IM Image Object in “IM Image Object” on page 573.
 * </p>
 */
public class BeginImageObject extends StructuredFieldWithTriplets {

    private final String idoName;

    public BeginImageObject(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        idoName = params.getString(0, 8);
    }

    /**
     * Returns the name of the IOCA image data object.
     *
     * @return the IOCA data
     */
    public String getIdoName() {
        return idoName;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ImageObjectName", idoName));
        return params;
    }
}
