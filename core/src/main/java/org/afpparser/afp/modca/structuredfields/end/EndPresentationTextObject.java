package org.afpparser.afp.modca.structuredfields.end;

import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The End Presentation Text Object structured field terminates the current presentation text object
 * initiated by a Begin Presentation Text Object structured field.
 */
public class EndPresentationTextObject extends StructuredFieldWithTriplets {

    public EndPresentationTextObject(SfIntroducer introducer, List<Triplet> triplets,
            byte[] sfData) {
        super(introducer, triplets);
    }

}
