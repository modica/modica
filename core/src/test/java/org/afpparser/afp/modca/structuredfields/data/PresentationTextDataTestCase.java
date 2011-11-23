package org.afpparser.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link PresentationTextData}.
 */
public class PresentationTextDataTestCase extends StructuredFieldTestCase<PresentationTextData> {

    private PresentationTextData sut;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Begin.BPT);

        sut = new PresentationTextData(intro, "Test text".getBytes("Cp500"));
        setMemebers(sut, intro);
    }

    @Test
    public void testGetText() throws UnsupportedEncodingException {
        assertEquals("Test text", sut.getPtocaData("Cp500"));
    }
}
