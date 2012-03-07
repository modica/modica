package org.modica.afp.modca.structuredfields.descriptor;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;

/**
 * The Object Area Descriptor structured field specifies the size and attributes of an object area
 * presentation space.
 */
public class ObjectAreaDescriptor extends StructuredFieldWithTriplets {

    public ObjectAreaDescriptor(StructuredFieldIntroducer introducer, List<Triplet> triplets) {
        super(introducer, triplets);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }
}
