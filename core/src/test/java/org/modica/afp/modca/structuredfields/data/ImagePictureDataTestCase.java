package org.modica.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Data;
import org.modica.afp.modca.structuredfields.data.ImagePictureData;

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
