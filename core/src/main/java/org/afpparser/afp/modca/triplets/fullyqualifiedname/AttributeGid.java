package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet contains the GID of a document attribute.
 */
public class AttributeGid extends FQNCharStringData {

    AttributeGid(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.attribute_gid;
    }
}
