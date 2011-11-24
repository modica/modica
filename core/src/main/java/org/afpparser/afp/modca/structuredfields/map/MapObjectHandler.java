package org.afpparser.afp.modca.structuredfields.map;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Map;
import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
import org.afpparser.afp.modca.triplets.TripletHandler;

/**
 * A handler for constructing {@link Map} type structured fields.
 */
public final class MapObjectHandler {

    private MapObjectHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case coded_font:
                RepeatingTripletGroup tripletGroup = TripletHandler.parseRepeatingGroup(sfData);
                sf = new MapCodedFont(intro, tripletGroup);
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
