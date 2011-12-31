package org.afpparser.afp.modca.structuredfields.descriptor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.Context;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.structuredfields.migration.PresentationTextDataDescriptor;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletHandler;

public class DescriptorHandler {

    private DescriptorHandler() {
    }

    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params, Context context) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case code_page:
                sf = new CodePageDescriptor(intro, params);
                break;
            case font:
                triplets = TripletHandler.parseTriplet(params, 80, context);
                sf = new FontDescriptor(intro, triplets, params);
                break;
            case image:
                sf = new ImageDataDescriptor(intro, params);
                break;
            case object_area:
                triplets = TripletHandler.parseTriplet(params, 0, context);
                sf = new ObjectAreaDescriptor(intro, triplets);
                break;
            case page:
                triplets = TripletHandler.parseTriplet(params, 15, context);
                sf = new PageDescriptor(intro, triplets, params);
                break;
            case presentation_text:
                sf = new PresentationTextDataDescriptor(intro, params);
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
