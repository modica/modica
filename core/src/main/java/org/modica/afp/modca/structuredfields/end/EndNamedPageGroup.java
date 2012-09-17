package org.modica.afp.modca.structuredfields.end;

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
 * The End Resource Group structured field terminates the definition of a resource group initiated
 * by a Begin Resource Group structured field.
 */
public class EndNamedPageGroup extends StructuredFieldWithTriplets {

    private final EndFieldName pGrpName;

    public EndNamedPageGroup(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        pGrpName = new EndFieldName(params);
    }

    /**
     * Is the name of the page group that is being terminated. If a name is specified, it must match
     * the name in the most recent Begin Named Page Group structured field in the document or a
     * X’01’ exception condition exists. If the first two bytes of PGrpName contain the value
     * X'FFFF', the name matches any name specified on the Begin Named Page Group structured field
     * that initiated the current definition
     *
     * @return the Page Group Name
     */
    public String getPGrpName() {
        return pGrpName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the BeginPageGroup
     * object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return pGrpName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("PageGroupName", getPGrpName()));
        return params;
    }

    public static final class ENGBuilder implements Builder {
        @Override
        public EndNamedPageGroup create(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new EndNamedPageGroup(intro, TripletHandler.parseTriplet(params, 8, context), params);
        }
    }
}
