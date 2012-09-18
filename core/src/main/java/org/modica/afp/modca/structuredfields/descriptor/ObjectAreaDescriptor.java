package org.modica.afp.modca.structuredfields.descriptor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Object Area Descriptor structured field specifies the size and attributes of an object area
 * presentation space.
 */
public class ObjectAreaDescriptor extends StructuredFieldWithTriplets {

    ObjectAreaDescriptor(StructuredFieldIntroducer introducer, List<Triplet> triplets) {
        super(introducer, triplets);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }

    public static final class OBDBuilder implements Builder {
        @Override
        public ObjectAreaDescriptor build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new ObjectAreaDescriptor(intro, TripletHandler.parseTriplet(params, 0, context));
        }
    }
}
