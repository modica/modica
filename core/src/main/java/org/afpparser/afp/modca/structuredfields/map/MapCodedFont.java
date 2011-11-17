package org.afpparser.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.afpparser.afp.modca.AbstractStructuredField;
import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.ByteUtils;

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

    public MapCodedFont(SfIntroducer introducer, byte[] sfData)
            throws UnsupportedEncodingException, MalformedURLException {
        super(introducer, Triplet.parseTriplet(sfData, 2,
                ByteUtils.newLittleEndianUtils().bytesToUnsignedInt(sfData, 0, 2)));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Triplet t : getTriplets()) {
            sb.append(t.toString());
        }
        return getType().getName() + " triplets=" + sb.toString();
    }

}
