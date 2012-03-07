package org.modica.afp.modca.structuredfields;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.triplets.Triplet;

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
    public List<List<ParameterAsString>> getTripletParameters() {
        List<List<ParameterAsString>> tripletStrings = new ArrayList<List<ParameterAsString>>();
        for (Triplet t : getTriplets()) {
            tripletStrings.add(t.getParameters());
        }
        return tripletStrings;
    }
}
