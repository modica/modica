/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    ImagePictureData(StructuredFieldIntroducer introducer) {
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

    public static final class IPDBuilder implements Builder {
        @Override
        public ImagePictureData build(StructuredFieldIntroducer intro, Parameters params,
                Context context) {
            return new ImagePictureData(intro);
        }
    }
}
