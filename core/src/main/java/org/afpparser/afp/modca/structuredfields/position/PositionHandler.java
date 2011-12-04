package org.afpparser.afp.modca.structuredfields.position;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class PositionHandler {

    private PositionHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, Parameters params) {
        StructuredField sf;
        switch (intro.getType().getCategoryCode()) {
        case object_area:
            sf = new ObjectAreaPosition(intro, params);
            break;
        default:
            sf = null;
        }
        return sf;
    }
}
