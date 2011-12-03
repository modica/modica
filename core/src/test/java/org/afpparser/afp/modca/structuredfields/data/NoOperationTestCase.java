package org.afpparser.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Data;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link NoOperation}.
 */
public class NoOperationTestCase extends StructuredFieldTestCase<NoOperation> {

    private NoOperation sut;
    private final String comment = "This comment can be of arbitrary length";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Data.NOP);

        byte[] sfData = comment.getBytes("Cp500");
        sut = new NoOperation(intro, sfData);
        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethod() throws UnsupportedEncodingException {
        assertEquals(comment, sut.getComment("Cp500"));
    }
}
