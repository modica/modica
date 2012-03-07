package org.modica.afp.modca.structuredfields.map;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletGroup;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;

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
}
