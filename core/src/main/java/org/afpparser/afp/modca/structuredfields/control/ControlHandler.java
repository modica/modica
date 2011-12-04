package org.afpparser.afp.modca.structuredfields.control;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class ControlHandler {
    private ControlHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, Parameters params) {
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case code_page:
                sf = new CodePageControl(intro, params);
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
