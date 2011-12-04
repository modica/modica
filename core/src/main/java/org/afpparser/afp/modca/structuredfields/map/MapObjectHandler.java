package org.afpparser.afp.modca.structuredfields.map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Map;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
import org.afpparser.afp.modca.triplets.TripletHandler;

/**
 * A handler for constructing {@link Map} type structured fields.
 */
public final class MapObjectHandler {

    private MapObjectHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, Parameters params) {
        try {
            StructuredField sf;
            RepeatingTripletGroup tripletGroup;
            switch (intro.getType().getCategoryCode()) {
            case coded_font:
                tripletGroup = TripletHandler.parseRepeatingGroup(params);
                sf = new MapCodedFont(intro, tripletGroup);
                break;
            case image:
                tripletGroup = TripletHandler.parseRepeatingGroup(params);
                sf = new MapImageObject(intro, tripletGroup);
                break;
            default:
                sf = null;
            }
            return sf;
        } catch (Exception uee) {
            throw new IllegalStateException(uee);
        }
    }
}
