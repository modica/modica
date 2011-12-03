package org.afpparser.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class DataHandler {

    private DataHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        StructuredField sf;
        try {
            switch (intro.getType().getCategoryCode()) {
            case image:
                sf = new ImagePictureData(intro);
                break;
            case presentation_text:
                sf = new PresentationTextData(intro, sfData);
                break;
            case no_operation:
                sf = new NoOperation(intro, sfData);
                break;
            default:
                sf = null;
            }
            return sf;
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(uee);
        }
    }
}
