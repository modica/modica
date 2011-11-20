package org.afpparser.afp.modca.structuredfields;

import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;

/**
 * An abstract class for structured fields with triplet group attachments.
 */
public abstract class StructuredFieldWithTripletGroup extends AbstractStructuredField {

    private final RepeatingTripletGroup tripletGroup;

    public StructuredFieldWithTripletGroup(SfIntroducer introducer,
            RepeatingTripletGroup tripletGroup) {
        super(introducer);
        this.tripletGroup = tripletGroup;
    }

    /**
     * Returns true if the triplet group contains any triplets.
     *
     * @return true if the triplet group contains triplets
     */
    public final boolean hasTripletGroup() {
        return tripletGroup != null && tripletGroup.size() > 0;
    }

    /**
     * Returns the triplet group.
     *
     * @return the triplet group
     */
    public final RepeatingTripletGroup getTripletGroup() {
        return tripletGroup;
    }
}
