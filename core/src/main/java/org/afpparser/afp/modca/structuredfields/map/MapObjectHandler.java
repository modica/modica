package org.afpparser.afp.modca.structuredfields.map;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.StructuredField;

public class MapObjectHandler {
    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case coded_font:
                sf = new MapCodedFont(intro, sfData);
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
