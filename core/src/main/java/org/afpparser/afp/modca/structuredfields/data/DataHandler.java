package org.afpparser.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Context;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class DataHandler {

    private DataHandler() {
    }

    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params, Context context) {
        StructuredField sf;
        try {
            switch (intro.getType().getCategoryCode()) {
            case image:
                sf = new ImagePictureData(intro);
                break;
            case presentation_text:
                sf = new PresentationTextData(intro, params);
                break;
            case no_operation:
                sf = new NoOperation(intro, params);
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
