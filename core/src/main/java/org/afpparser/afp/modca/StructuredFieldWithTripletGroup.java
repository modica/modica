package org.afpparser.afp.modca;

import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;

public class StructuredFieldWithTripletGroup extends AbstractStructuredField {

    private final RepeatingTripletGroup tripletGroup;

    public StructuredFieldWithTripletGroup(SfIntroducer introducer,
            RepeatingTripletGroup tripletGroup) {
        super(introducer);
        this.tripletGroup = tripletGroup;
    }

    public final boolean hasTripletGroup() {
        return tripletGroup != null;
    }

    public final RepeatingTripletGroup getTripletGroup() {
        return tripletGroup;
    }
}
