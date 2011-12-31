package org.afpparser.afp.modca.structuredfields.migration;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class MigrationHandler {

    private MigrationHandler() {
    }

    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params) {
        StructuredField sf;
        switch (intro.getType().getCategoryCode()) {
        case presentation_text:
            sf = new PresentationTextDataDescriptor(intro, params);
            break;
        default:
            sf = null;
        }
        return sf;
    }
}
