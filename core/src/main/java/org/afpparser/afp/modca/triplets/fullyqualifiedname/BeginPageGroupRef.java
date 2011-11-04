package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet contains a GID reference to a Begin Named Page Group structured field.
 */
public class BeginPageGroupRef extends FQNCharStringData {

    BeginPageGroupRef(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.begin_page_group_ref;
    }
}
