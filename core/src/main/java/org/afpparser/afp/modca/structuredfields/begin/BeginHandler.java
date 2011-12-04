package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletHandler;

/**
 * A handler for constructing {@link Begin} type structured fields.
 */
public final class BeginHandler {

    private BeginHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, Parameters params) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case active_environment_group:
                triplets = TripletHandler.parseTriplet(params, 8);
                sf = new BeginActiveEnvironmentGroup(intro, triplets, params);
                break;
            case code_page:
                triplets = TripletHandler.parseTriplet(params, 8);
                sf = new BeginCodePage(intro, triplets, params);
                break;
            case document:
                triplets = TripletHandler.parseTriplet(params, 8);
                sf = new BeginDocument(intro, triplets, params);
                break;
            case image:
                triplets = TripletHandler.parseTriplet(params, 8);
                sf = new BeginImageObject(intro, triplets, params);
                break;
            case object_environment_group:
                triplets = TripletHandler.parseTriplet(params, 8);
                sf = new BeginObjectEnvironmentGroup(intro, triplets, params);
                break;
            case page_group:
                triplets = TripletHandler.parseTriplet(params, 8);
                sf = new BeginNamedPageGroup(intro, triplets, params);
                break;
            case page:
                triplets = TripletHandler.parseTriplet(params, 8);
                sf = new BeginPage(intro, triplets, params);
                break;
            case presentation_text:
                triplets = TripletHandler.parseTriplet(params, 8);
                sf = new BeginPresentationTextObject(intro, triplets, params);
                break;
            case name_resource:
                triplets = TripletHandler.parseTriplet(params, 10);
                sf = new BeginResource(intro, triplets, params);
                break;
            case resource_group:
                triplets = TripletHandler.parseTriplet(params, 8);
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
