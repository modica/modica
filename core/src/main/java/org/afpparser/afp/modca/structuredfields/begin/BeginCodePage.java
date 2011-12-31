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
    public String toString() {
        return getType().getName() + " cfName=" + cfName + tripletsToString();
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("CodePageName", cfName);
        return params;
    }
}
