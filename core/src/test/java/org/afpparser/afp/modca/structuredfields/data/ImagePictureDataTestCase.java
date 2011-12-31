package org.afpparser.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Data;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link ImagePictureData}.
 */
public class ImagePictureDataTestCase extends StructuredFieldTestCase<ImagePictureData> {

    private ImagePictureData sut;
    private StructuredFieldIntroducer intro;

    @Before
    public void setUp() {
        intro = SfIntroducerTestCase.createGenericIntroducer(Data.IPD);
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
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("ImageDataOffset", "0000000f");
        testParameters(expectedParams, sut);
    }
}
