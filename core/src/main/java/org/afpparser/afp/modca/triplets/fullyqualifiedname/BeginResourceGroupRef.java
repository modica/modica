package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet contains a GID reference to a Begin Resource Group structured field.
 */
public class BeginResourceGroupRef extends FQNCharStringData {

    BeginResourceGroupRef(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.begin_resource_group_ref;
    }

}
