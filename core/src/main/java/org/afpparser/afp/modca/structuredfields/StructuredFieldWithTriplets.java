package org.afpparser.afp.modca.structuredfields;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.triplets.Triplet;

/**
 * An abstract class for structured fields that have a linear list of triplets.
 */
public abstract class StructuredFieldWithTriplets extends AbstractStructuredField {

    private final List<Triplet> triplets;

    public StructuredFieldWithTriplets(StructuredFieldIntroducer introducer, List<Triplet> triplets) {
        super(introducer);
        this.triplets = Collections.unmodifiableList(triplets);
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

    public String tripletsToString() {
        StringBuilder sb = new StringBuilder();
        for (Triplet t : getTriplets()) {
            sb.append("\t");
            sb.append(t.toString());
            sb.append("\n");
        }
        return " triplets=" + sb.toString();
    }

    /**
     * Returns a map of triplet names and their corresponding values.
     *
     * @return the triplets as Strings
     */
    public Map<String, String> getTripletsAsStrings() {
        Map<String, String> tripletStrings = new HashMap<String, String>();
        for (Triplet t : getTriplets()) {
            tripletStrings.put(t.getTid().getName(), t.getValueAsString());
        }
        return tripletStrings;
    }
}
