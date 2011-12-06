package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The Begin Page structured field begins a presentation page. A presentation page contains an
 * active environment group to establish parameters such as the size of the page’s presentation
 * space and the fonts to be used by the data objects. It may also contain any mixture of:
 * <p> - Bar code objects</p>
 * <p> - Graphics objects</p>
 * <p> - Image objects</p>
 * <p> - Object containers</p>
 * <p> - Presentation text objects</p>
 */
public class BeginPage extends StructuredFieldWithTriplets {

    private final String pageName;

    public BeginPage(SfIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        pageName = params.getString(0, 8);
    }

    /**
     * Returns the name of the page.
     *
     * @return the name of the page
     */
    public String getPageName() {
        return pageName;
    }

    @Override
    public String toString() {
        return getType().getName() + " page-name=" + pageName;
    }

}
