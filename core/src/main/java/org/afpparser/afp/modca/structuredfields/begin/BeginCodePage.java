package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Coded Font (BCF) structured field begins a coded font object.
 */
public class BeginCodePage extends StructuredFieldWithTriplets {

    private final String cfName;

    public BeginCodePage(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        cfName = StringUtils.bytesToCp500(sfData, 0, 8);
    }

    /**
     * Returns the Code Page Name.
     *
     * @return the code page name
     */
    public String getCFName() {
        return cfName;
    }

    @Override
    public String toString() {
        return getType().getName() + " cfName=" + cfName + tripletsToString();
    }
}
