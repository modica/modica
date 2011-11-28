package org.afpparser.afp.modca.structuredfields.descriptor;

import java.util.List;

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
}
