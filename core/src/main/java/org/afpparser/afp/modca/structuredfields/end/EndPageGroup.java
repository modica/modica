package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.ByteUtils;
import org.afpparser.common.StringUtils;

/**
 * The End Resource Group structured field terminates the definition of a resource group initiated
 * by a Begin Resource Group structured field.
 */
public class EndPageGroup extends StructuredFieldWithTriplets {

    private final String rGrpName;
    private final boolean nameMatchesAny;

    public EndPageGroup(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        if (ByteUtils.arrayEqualsSubset(sfData, 0xff, 0xff)) {
            rGrpName = null;
            nameMatchesAny = true;
        } else {
            rGrpName = StringUtils.bytesToCp500(sfData, 0, 8);
            nameMatchesAny = false;
        }
    }

    /**
     * Returns the name of the resource group that is being terminated. If a name is specified, it
     * must match the name in the most recent Begin Resource Group structured field in the print
     * file, document, page, or data object, or a X’01’ exception condition exists. If the first two
     * bytes of RGrpName contain the value X'FFFF', the name matches any name specified on the Begin
     * Resource Group structured field that initiated the current definition.
     *
     * @return the Page Group Name
     */
    public String getRGrpName() {
        return rGrpName;
    }

    /**
     * Returns true if the page name should match any corresponding page name on the BeginPageGroup
     * object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return nameMatchesAny;
    }

    @Override
    public String toString() {
        return getType().toString() + " RGrpName=" + rGrpName;
    }
}
