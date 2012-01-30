package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The End Page structured field terminates the current presentation page definition initiated by a
 * Begin Page structured field.
 */
public class EndPage extends StructuredFieldWithTriplets {

    private final EndFieldName pageName;

    public EndPage(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        pageName = new EndFieldName(params);
    }

    /**
     * Returns the name of the page that is being terminated. If a name is specified, it must match
     * the name in the most recent Begin Page structured field in the document or a X’01’ exception
     * condition exists. If the first two bytes of PageName contain the value X'FFFF', the name
     * matches any name specified on the Begin Page structuredfield that initiated the current
     * definition.
     *
     * @return the Page Name
     */
    public String getPageName() {
        return pageName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the BeginPage
     * object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return pageName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("PageName", getPageName()));
        return params;
    }
}
