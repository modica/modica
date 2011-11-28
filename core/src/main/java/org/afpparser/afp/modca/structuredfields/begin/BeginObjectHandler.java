package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletHandler;

/**
 * A handler for constructing {@link Begin} type structured fields.
 */
public final class BeginObjectHandler {

    private BeginObjectHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case active_environment_group:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginActiveEnvironmentGroup(intro, triplets, sfData);
                break;
            case document:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginDocument(intro, triplets, sfData);
                break;
            case image:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginImageObject(intro, triplets, sfData);
                break;
            case page_group:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginNamedPageGroup(intro, triplets, sfData);
                break;
            case page:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginPage(intro, triplets, sfData);
                break;
            case presentation_text:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginPresentationTextObject(intro, triplets, sfData);
                break;
            case name_resource:
                triplets = TripletHandler.parseTriplet(sfData, 10);
                sf = new BeginResource(intro, triplets, sfData);
                break;
            case resource_group:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginResourceGroup(intro, triplets, sfData);
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
