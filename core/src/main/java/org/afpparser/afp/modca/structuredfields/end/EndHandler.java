package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.Context;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletHandler;

/**
 * A handler for constructing {@link End} type structured fields.
 */
public class EndHandler {

    private EndHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, Parameters params, Context context) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case active_environment_group:
                sf = new EndActiveEnvironmentGroup(intro, params);
                break;
            case code_page:
                sf = new EndCodePage(intro, params);
                break;
            case document:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new EndDocument(intro, triplets, params);
                break;
            case image:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new EndImageObject(intro, triplets, params);
                break;
            case name_resource:
                sf = new EndResource(intro, params);
                break;
            case object_environment_group:
                sf = new EndObjectEnvironmentGroup(intro, params);
                break;
            case page:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new EndPage(intro, triplets, params);
                break;
            case page_group:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new EndPageGroup(intro, triplets, params);
                break;
            case presentation_text:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new EndPresentationTextObject(intro, triplets, params);
                break;
            case resource_group:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new EndResourceGroup(intro, triplets, params);
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
