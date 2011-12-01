package org.afpparser.afp.modca.structuredfields.data;

import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;

/**
 * The Image Picture Data structured field contains the data for an image data object. This class
 * is an empty wrapper for the structured field introducer, it merely points to the offset of the
 * start of the image data.
 */
public class ImagePictureData extends AbstractStructuredField {

    private final long imageDataOffset;

    public ImagePictureData(SfIntroducer introducer) {
        super(introducer);
        imageDataOffset = introducer.getDataOffset();
    }

    /**
     * The byte offset of the image data.
     *
     * @return the image data offset
     */
    public long getImageDataOffset() {
        return imageDataOffset;
    }

    @Override
    public String toString() {
        return getType().getName() + " dataOffset=" + imageDataOffset;
    }
}
