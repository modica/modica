package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * A handler for constructing {@link Begin} type structured fields.
 */
public final class BeginHandler {

    private BeginHandler() {
    }

    public static StructuredField handle(StructuredFieldIntroducer intro, Parameters params, Context context) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case active_environment_group:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginActiveEnvironmentGroup(intro, triplets, params);
                break;
            case code_page:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginCodePage(intro, triplets, params, context);
                break;
            case document:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginDocument(intro, triplets, params);
                break;
            case font:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginFont(intro, triplets, params);
                break;
            case image:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginImageObject(intro, triplets, params);
                break;
            case object_environment_group:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginObjectEnvironmentGroup(intro, triplets, params);
                break;
            case page_group:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginNamedPageGroup(intro, triplets, params);
                break;
            case page:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginPage(intro, triplets, params);
                break;
            case presentation_text:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginPresentationTextObject(intro, triplets, params);
                break;
            case name_resource:
                triplets = TripletHandler.parseTriplet(params, 10, context);
                sf = new BeginResource(intro, triplets, params);
                break;
            case resource_group:
                triplets = TripletHandler.parseTriplet(params, 8, context);
                sf = new BeginResourceGroup(intro, triplets, params);
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
