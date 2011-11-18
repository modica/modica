package org.afpparser.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.AbstractStructuredField;
import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.Triplet.RepeatingGroup;

/**
 * The Map Coded Font structured field maps a unique coded font resource local ID, which may be
 * embedded one or more times within an object’s data and descriptor, to the identifier of a coded
 * font resource object. This identifier may be specified in one of the following formats:
 * <p> - A coded font Global Resource Identifier (GRID)</p>
 * <p> - A coded font name</p>
 * <p> - A combination of code page name and font character set name</p>
 * Additionally, the Map Coded Font structured field specifies a set of resource attributes for the
 * coded font.
 */
public class MapCodedFont extends AbstractStructuredField {

    private final RepeatingGroup repeatingTriplets;

    public MapCodedFont(SfIntroducer introducer, byte[] sfData)
            throws UnsupportedEncodingException, MalformedURLException {
        super(introducer);
        repeatingTriplets = Triplet.parseRepeatingGroup(sfData);
    }

    public RepeatingGroup getRepeatingGroup() {
        return repeatingTriplets;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<Triplet> tripletList : repeatingTriplets.getRepeatingGroup()) {
            for (Triplet t : tripletList) {
                sb.append("\t");
                sb.append(t.toString());
                sb.append("\n");
            }
        }
        return getType().getName() + " triplets=" + sb.toString();
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
