package org.afpparser.afp.modca.structuredfields.data;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class DataObjectHandler {

    private DataObjectHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        StructuredField sf;
        switch (intro.getType().getCategoryCode()) {
        case image:
            sf = new ImagePictureData(intro);
            break;
        case presentation_text:
            sf = new PresentationTextData(intro, sfData);
            break;
        default:
            sf = null;
        }
        return sf;
    }
}
