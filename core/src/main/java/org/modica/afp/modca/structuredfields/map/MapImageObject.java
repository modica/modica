package org.modica.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletGroup;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Map Image Object structured field specifies how an image data object is mapped into its
 * object area.
 */
public class MapImageObject extends StructuredFieldWithTripletGroup {

    public MapImageObject(StructuredFieldIntroducer introducer, RepeatingTripletGroup tripletGroup) {
        super(introducer, tripletGroup);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }

    public class MIOBuilder implements Builder {
        @Override
        public StructuredField create(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new MapImageObject(intro, TripletHandler.parseRepeatingGroup(params, context));
        }
    }
}
