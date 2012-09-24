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

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.types.DataType;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link ImagePictureData}.
 */
public class ImagePictureDataTestCase extends StructuredFieldTestCase<ImagePictureData> {

    private ImagePictureData sut;
    private StructuredFieldIntroducer intro;

    @Before
    public void setUp() {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(DataType.IPD);
        sut = new ImagePictureData(intro);
        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethod() {
        assertEquals(intro.getDataOffset(), sut.getImageDataOffset());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ImageDataOffset", "0000000f"));
        testParameters(expectedParams, sut);
    }
}
