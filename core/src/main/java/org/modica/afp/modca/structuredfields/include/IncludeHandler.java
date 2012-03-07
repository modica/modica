package org.modica.afp.modca.structuredfields.include;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

public class IncludeHandler {
    private IncludeHandler() {
    }

    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params, Context context) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case data_resource:
                triplets = TripletHandler.parseTriplet(params, 27, context);
                sf = new IncludeObject(intro, triplets, params);
                break;
            default:
                sf = null;
            }
            return sf;
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(uee);
        } catch (MalformedURLException mue) {
            throw new IllegalStateException(mue);
        }
    }
}