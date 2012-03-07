package org.modica.afp.modca.structuredfields.migration;

import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

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
