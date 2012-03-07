package org.modica.afp.modca.structuredfields.index;

import java.io.UnsupportedEncodingException;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

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
