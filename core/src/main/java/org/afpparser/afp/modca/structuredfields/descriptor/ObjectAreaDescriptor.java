package org.afpparser.afp.modca.structuredfields.descriptor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The Object Area Descriptor structured field specifies the size and attributes of an object area
 * presentation space.
 */
public class ObjectAreaDescriptor extends StructuredFieldWithTriplets {

    public ObjectAreaDescriptor(SfIntroducer introducer, List<Triplet> triplets) {
        super(introducer, triplets);
    }

    @Override
    public String toString() {
        return getType().toString() + tripletsToString();
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        return params;
    }
}
