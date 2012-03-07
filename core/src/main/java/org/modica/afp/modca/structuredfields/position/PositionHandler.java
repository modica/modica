package org.modica.afp.modca.structuredfields.position;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

public class PositionHandler {

    private PositionHandler() {
    }

    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params, Context context) {
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
