package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

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
    public String toString() {
        return getType().toString() + " RGrpName=" + pGrpName;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("PageGroupName", getPGrpName());
        return params;
    }
}
