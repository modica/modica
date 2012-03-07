package org.modica.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

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
