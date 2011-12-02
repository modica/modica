package org.afpparser.afp.modca.structuredfields.descriptor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.structuredfields.migration.PresentationTextDataDescriptor;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletHandler;

public class DescriptorHandler {

    private DescriptorHandler() {
    }

    public static StructuredField handle(SfIntroducer intro, byte[] sfData) {
        List<Triplet> triplets;
        try {
            StructuredField sf;
            switch (intro.getType().getCategoryCode()) {
            case image:
                sf = new ImageDataDescriptor(intro, sfData);
                break;
            case object_area:
                triplets = TripletHandler.parseTriplet(sfData, 0);
                sf = new ObjectAreaDescriptor(intro, triplets);
                break;
            case page:
                triplets = TripletHandler.parseTriplet(sfData, 15);
                sf = new PageDescriptor(intro, triplets, sfData);
                break;
            case presentation_text:
                sf = new PresentationTextDataDescriptor(intro, sfData);
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
