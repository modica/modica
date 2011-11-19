package org.afpparser.afp.modca.structuredfields.map;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.StructuredField;
import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
import org.afpparser.afp.modca.triplets.TripletHandler;

public class MapObjectHandler {
    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case coded_font:
                RepeatingTripletGroup tripletGroup = TripletHandler.parseRepeatingGroup(sfData);
                sf = new MapCodedFont(intro, tripletGroup, sfData);
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
