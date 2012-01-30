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
 * The Begin Coded Font (BCF) structured field begins a coded font object.
 */
public class BeginCodePage extends StructuredFieldWithTriplets {

    private final String cfName;

    public BeginCodePage(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        cfName = params.getString(0, 8, "Cp500");
    }

    /**
     * Returns the Code Page Name.
     *
     * @return the code page name
     */
    public String getCFName() {
        return cfName;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("CodePageName", cfName));
        return params;
    }
}
