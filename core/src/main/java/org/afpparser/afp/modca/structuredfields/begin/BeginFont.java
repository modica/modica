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
 * The Begin Font (BFN) structured field begins the font character set object.
 */
public class BeginFont extends StructuredFieldWithTriplets {

    private final String csName;

    public BeginFont(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        csName = params.getString(0, 8, "Cp500");
    }

    /**
     * Returns the Font Character Set Name.
     * <p>
     * If used, it is suggested that the font character set name be the same name used to reference
     * the object. See bytes 0–7 of the Coded Font Index (CFI) repeating group in this document, or
     * the Map Coded Font (MCF) of the Mixed Object Document Content Architecture Reference,
     * SC31-6802.
     * </p>
     *
     * @return the font character set name
     */
    public String getCharacterSetName() {
        return csName;
    }

    @Override
    public String toString() {
        return getType().getName() + " charsetName=" + csName;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("CharactersetName", csName);
        return params;
    }
}
