package org.afpparser.afp.modca;

import java.util.List;

import org.afpparser.afp.modca.triplets.Triplet;

public abstract class StructuredFieldWithTriplets extends AbstractStructuredField {

    private final List<Triplet> triplets;

    public StructuredFieldWithTriplets(SfIntroducer introducer, List<Triplet> triplets) {
        super(introducer);
        this.triplets = triplets;
    }

    /**
     * Checks if this structured field has triplets bound to it.
     *
     * @return true if this structured field has triplets
     */
    public final boolean hasTriplets() {
        return triplets != null && triplets.size() > 0;
    }

    /**
     * Gets a view to the triplets as an unmodifiable List.
     *
     * @return the triplets bound to this structured field
     */
    public final List<Triplet> getTriplets() {
        return triplets;
    }
}
