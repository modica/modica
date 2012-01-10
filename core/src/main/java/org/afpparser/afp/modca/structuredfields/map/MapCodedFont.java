package org.afpparser.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletGroup;
import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;

/**
 * The Map Coded Font structured field maps a unique coded font resource local ID, which may be
 * embedded one or more times within an object’s data and descriptor, to the identifier of a coded
 * font resource object. This identifier may be specified in one of the following formats:
 * <p> - A coded font Global Resource Identifier (GRID)</p>
 * <p> - A coded font name</p>
 * <p> - A combination of code page name and font character set name</p>
 * Additionally, the Map Coded Font structured field specifies a set of resource attributes for the
 * coded font.
 */
public class MapCodedFont extends StructuredFieldWithTripletGroup {

    public MapCodedFont(StructuredFieldIntroducer introducer, RepeatingTripletGroup tripletGroup)
            throws UnsupportedEncodingException, MalformedURLException {
        super(introducer, tripletGroup);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }
}
