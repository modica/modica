package org.afpparser.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
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
    private SfIntroducer intro;

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
}
