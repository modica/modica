package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.AbstractStructuredField;
import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Named Page Group structured field begins a page group, which is a named, logical
 * grouping of sequential pages. A page group may contain other nested page groups. All pages in
 * the page group and all other page groups that are nested in the page group inherit the attributes
 * that are assigned to the page group using TLE structured fields.
 */
public class BeginNamedPageGroup extends AbstractStructuredField {

    private final String pGrpName;

    public BeginNamedPageGroup(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer);
        pGrpName = StringUtils.bytesToCp500(sfData);
    }

    public String getPageGroupName() {
        return pGrpName;
    }

    public String toString() {
        return getType().getName() + " page-group-name=" + pGrpName;
    }

    @Override
    public boolean hasTriplets() {
        return false;
    }

    @Override
    public List<Triplet> getTriplets() {
        return null;
    }
}
