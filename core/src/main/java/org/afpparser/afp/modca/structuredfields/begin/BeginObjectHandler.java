package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.StructuredField;

public class BeginObjectHandler {

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case document:
                sf = new BeginDocument(intro, sfData);
                break;
            case page_group:
                sf = new BeginNamedPageGroup(intro, null, sfData);
                break;
            case page:
                sf = new BeginPage(intro, null, sfData);
                break;
            case active_environment_group:
                sf = new BeginActiveEnvironmentGroup(intro, sfData);
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
