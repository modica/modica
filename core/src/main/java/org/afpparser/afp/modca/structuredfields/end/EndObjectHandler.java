package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletHandler;

public class EndObjectHandler {

    private EndObjectHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case active_environment_group:
                sf = new EndActiveEnvironmentGroup(intro, sfData);
                break;
            case document:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new EndDocument(intro, triplets, sfData);
                break;
            case object_environment_group:
                sf = new EndObjectEnvironmentGroup(intro, sfData);
                break;
            case page:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new EndPage(intro, triplets, sfData);
                break;
            case page_group:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new EndPageGroup(intro, triplets, sfData);
                break;
            case presentation_text:
                triplets = TripletHandler.parseTriplet(sfData, 8);
                sf = new EndPresentationTextObject(intro, triplets, sfData);
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
