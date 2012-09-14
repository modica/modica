package org.modica.afp.modca.structuredfields.data;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.common.StringUtils;

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

    public class IPDBuilder implements Builder {
        @Override
        public ImagePictureData create(StructuredFieldIntroducer intro, Parameters params,
                Context context) {
            return new ImagePictureData(intro);
        }
    }
}
