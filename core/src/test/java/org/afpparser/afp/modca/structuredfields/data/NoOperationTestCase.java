package org.afpparser.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Data;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
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
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Data.NOP);

        Parameters params = new Parameters(comment.getBytes("Cp500"), "Cp500");
        sut = new NoOperation(intro, params);
        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethod() throws UnsupportedEncodingException {
        assertEquals(comment, sut.getComment("Cp500"));
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("Comment", comment));
        testParameters(expectedParams, sut);
    }
}
