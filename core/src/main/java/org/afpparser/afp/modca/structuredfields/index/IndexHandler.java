package org.afpparser.afp.modca.structuredfields.index;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Context;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class IndexHandler {
    private IndexHandler() {
    }

    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params, Context context) {
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case code_page:
                sf = new CodePageIndex(intro, params, context);
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
