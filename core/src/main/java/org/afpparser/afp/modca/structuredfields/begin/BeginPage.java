package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

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

    public BeginPage(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        pageName = StringUtils.bytesToCp500(sfData);
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
