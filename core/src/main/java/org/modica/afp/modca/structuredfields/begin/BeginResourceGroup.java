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
 * The Begin Resource Group structured field begins a resource group, which becomes the current
 * resource group at the same level in the document hierarchy.
 */
public class BeginResourceGroup extends StructuredFieldWithTriplets {

    private final String rGrpName;

    BeginResourceGroup(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        rGrpName = params.getStringAt(0, 8);
    }

    /**
     * Returns the name of the resource group.
     *
     * @return the resource group name
     */
    public String getResourceGroupName() {
        return rGrpName;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ResourceGroupName", rGrpName));
        return params;
    }

    public static final class BRGBuilder implements Builder {
        @Override
        public BeginResourceGroup build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new BeginResourceGroup(intro, TripletHandler.parseTriplet(params, 8, context), params);
        }
    }
}
