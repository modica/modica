package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;

public class EndObjectHandler {

    private EndObjectHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case active_environment_group:
                sf = new EndActiveEnvironmentGroup(intro, sfData);
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
