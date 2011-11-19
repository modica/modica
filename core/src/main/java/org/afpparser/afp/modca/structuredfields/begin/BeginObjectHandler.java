package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.SfTypeFactory.Begin;
import org.afpparser.afp.modca.StructuredField;
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
            case document:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginDocument(intro, triplets, sfData);
                break;
            case page_group:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginNamedPageGroup(intro, triplets, sfData);
                break;
            case page:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginPage(intro, triplets, sfData);
                break;
            case active_environment_group:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new BeginActiveEnvironmentGroup(intro, triplets, sfData);
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