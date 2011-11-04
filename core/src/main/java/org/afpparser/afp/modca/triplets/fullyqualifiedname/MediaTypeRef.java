package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet contains a GID reference to a media type.
 */
public class MediaTypeRef extends FQNCharStringData {

    MediaTypeRef(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.media_type_ref;
    }
}
