package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Resource Group structured field begins a resource group, which becomes the current
 * resource group at the same level in the document hierarchy.
 */
public class BeginResourceGroup extends StructuredFieldWithTriplets {

    private final String rGrpName;

    public BeginResourceGroup(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        rGrpName = StringUtils.bytesToCp500(sfData, 0, 8);
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
    public String toString() {
        return getType().getName() + " resource-group-name=" + rGrpName;
    }
}
