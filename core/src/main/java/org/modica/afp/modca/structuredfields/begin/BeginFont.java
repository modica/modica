package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;

/**
 * The Begin Font (BFN) structured field begins the font character set object.
 */
public class BeginFont extends StructuredFieldWithTriplets {

    private final String csName;

    public BeginFont(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params) throws UnsupportedEncodingException {
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
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("CharactersetName", csName));
        return params;
    }
}
