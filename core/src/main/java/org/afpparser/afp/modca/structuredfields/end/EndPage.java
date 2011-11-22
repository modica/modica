package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The End Page structured field terminates the current presentation page definition initiated by a
 * Begin Page structured field.
 */
public class EndPage extends StructuredFieldWithTriplets {

    private final String pageName;
    private final boolean nameMatchesAny;

    public EndPage(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        if (sfData[0] == 0xff && sfData[1] == 0xff) {
            pageName = null;
            nameMatchesAny = true;
        } else {
            pageName = StringUtils.bytesToCp500(sfData, 0, 8);
            nameMatchesAny = false;
        }
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
        return pageName;
    }

    /**
     * Returns true if the page name should match any corresponding page name on the BeginPage
     * object.
     *
     * @return true if the page name should match any
     */
    public boolean getMatchesAny() {
        return nameMatchesAny;
    }

    @Override
    public String toString() {
        return getType().toString() + " PageName=" + pageName;
    }
}