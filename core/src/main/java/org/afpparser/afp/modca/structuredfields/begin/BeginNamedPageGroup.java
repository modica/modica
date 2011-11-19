package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Named Page Group structured field begins a page group, which is a named, logical
 * grouping of sequential pages. A page group may contain other nested page groups. All pages in
 * the page group and all other page groups that are nested in the page group inherit the attributes
 * that are assigned to the page group using TLE structured fields.
 */
public class BeginNamedPageGroup extends StructuredFieldWithTriplets {

    private final String pGrpName;

    public BeginNamedPageGroup(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        pGrpName = StringUtils.bytesToCp500(sfData);
    }

    /**
     * Return the name of the page group.
     *
     * @return the name of the page group
     */
    public String getPageGroupName() {
        return pGrpName;
    }

    @Override
    public String toString() {
        return getType().getName() + " page-group-name=" + pGrpName;
    }
}
