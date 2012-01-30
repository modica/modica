package org.afpparser.afp.modca.structuredfields.data;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.common.StringUtils;

/**
 * The Image Picture Data structured field contains the data for an image data object. This class
 * is an empty wrapper for the structured field introducer, it merely points to the offset of the
 * start of the image data.
 */
public class ImagePictureData extends AbstractStructuredField {

    private final long imageDataOffset;

    public ImagePictureData(StructuredFieldIntroducer introducer) {
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
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ImageDataOffset", StringUtils.toHex(imageDataOffset, 8)));
        return params;
    }
}
