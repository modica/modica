package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The End Image Object structured field terminates the current image object initiated by a Begin
 * Image Object structured field.
 */
public class EndImageObject extends StructuredFieldWithTriplets {

    private final EndFieldName idoName;

    public EndImageObject(SfIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        idoName = new EndFieldName(params);
    }

    /**
     * Returns the name of the image data object being terminated. If a name is specified, it must
     * match the name in the most recent Begin Image Object structured field in the containing page,
     * overlay, or resource group, or a X’01’ exception condition exists. If the first two bytes of
     * IdoName contain the value X'FFFF', the name matches any name specified on the Begin Image
     * Object structured field that initiated the current definition.
     *
     * @return the image data object name
     */
    public String getIdoName() {
        return idoName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the EndImageObject
     * object.
     *
     * @return true if the image object name should match any
     */
    public boolean nameMatchesAny() {
        return idoName.matchesAny();
    }

    @Override
    public String toString() {
        return getType().getName() + " idoName=" + idoName + tripletsToString();
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("ImageDataObjectName", getIdoName());
        return params;
    }
}
