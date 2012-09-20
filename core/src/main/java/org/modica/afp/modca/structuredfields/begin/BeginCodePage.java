package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.EbcdicStringHandler;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Begin Coded Font (BCF) structured field begins a coded font object.
 */
public class BeginCodePage extends StructuredFieldWithTriplets {

    private final String cfName;

    BeginCodePage(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params, Context ctx) throws UnsupportedEncodingException {
        super(introducer, triplets);
        cfName = params.getStringAt(0, 8, EbcdicStringHandler.DEFAULT_CPGID);
        ctx.setCurrentCodePageName(cfName);
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

    public static final class BCPBuilder implements Builder {
        @Override
        public BeginCodePage build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new BeginCodePage(intro, TripletHandler.parseTriplet(params, 8, context), params, context);
        }
    }
}
