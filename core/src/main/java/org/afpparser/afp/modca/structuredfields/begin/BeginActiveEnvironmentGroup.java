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
 * The Begin Active Environment Group structured field begins an Active Environment Group, which
 * establishes the environment parameters for the page or overlay. The scope of the active
 * environment group is the containing page or overlay.
 */
public final class BeginActiveEnvironmentGroup extends StructuredFieldWithTriplets {

    private final String aegName;

    public BeginActiveEnvironmentGroup(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params) throws UnsupportedEncodingException {
        super(introducer, triplets);
        aegName = params.getString(0, 8);
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
    public String toString() {
        return getType().getName() + " aeg-name=" + aegName + "\t" + tripletsToString();
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("ActiveEnvironmentGroupName", aegName);
        return params;
    }
}
