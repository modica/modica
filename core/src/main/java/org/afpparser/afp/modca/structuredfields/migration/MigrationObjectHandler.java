package org.afpparser.afp.modca.structuredfields.migration;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class MigrationObjectHandler {

    private MigrationObjectHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        StructuredField sf;
        switch (intro.getType().getCategoryCode()) {
        case presentation_text:
            sf = new PresentationTextDataDescriptor(intro, sfData);
            break;
        default:
            sf = null;
        }
        return sf;
    }
}
