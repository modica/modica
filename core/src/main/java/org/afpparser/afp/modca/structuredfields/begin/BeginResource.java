package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The Begin Resource structured field begins an envelope that is used to carry resource objects in
 * print-file-level (external) resource groups. Resource references in the data stream are matched
 * against the resource identifier specified by the Begin Resource structured field.
 * <p>
 * Note: To optimize print performance, it is strongly recommended that the same encoding scheme be
 * used for a resource reference wherever in a print file that resource reference is specified. That
 * is, the encoding scheme used for the resource include, the resource map, and the resource wrapper
 * should be the same. For TrueType/OpenType fonts, optimal performance can be achieved by using
 * UTF-16BE as the encoding scheme.
 * </p>
 */
public class BeginResource extends StructuredFieldWithTriplets {

    private final String rsName;

    public BeginResource(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        rsName = params.getString(0, 8);
    }

    /**
     * Is the identifier used to select the resource. This identifier is matched against the
     * resource reference in the data stream.
     *
     * @return the resource name
     */
    public String getRSName() {
        return rsName;
    }

    @Override
    public String toString() {
        return getType().getName() + " rsName=" + rsName + "\n" + tripletsToString();
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("ResourceName", rsName);
        return params;
    }
}
