package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Begin Active Environment Group structured field begins an Active Environment Group, which
 * establishes the environment parameters for the page or overlay. The scope of the active
 * environment group is the containing page or overlay.
 */
public final class BeginActiveEnvironmentGroup extends StructuredFieldWithTriplets {

    private final String aegName;

    public BeginActiveEnvironmentGroup(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params) throws UnsupportedEncodingException {
        super(introducer, triplets);
        aegName = params.getStringAt(0, 8);
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
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ActiveEnvironmentGroupName", aegName));
        return params;
    }

    public static final class BAGBuilder implements Builder {
        @Override
        public BeginActiveEnvironmentGroup create(StructuredFieldIntroducer intro,
                Parameters params, Context context) throws UnsupportedEncodingException,
                MalformedURLException {
            return new BeginActiveEnvironmentGroup(intro, TripletHandler.parseTriplet(params, 8,
                    context), params);
        }
    }
}
