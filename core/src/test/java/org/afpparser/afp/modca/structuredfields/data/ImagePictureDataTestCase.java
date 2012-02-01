package org.afpparser.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Data;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
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
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Data.IPD);
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
