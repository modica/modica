package org.modica.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.data.NoOperation;
import org.modica.afp.modca.structuredfields.types.DataType;

/**
 * Test case for {@link NoOperation}.
 */
public class NoOperationTestCase extends StructuredFieldTestCase<NoOperation> {

    private NoOperation sut;
    private final String comment = "This comment can be of arbitrary length";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(DataType.NOP);

        Parameters params = new Parameters(comment.getBytes("Cp500"));
        sut = new NoOperation(intro, params);
        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethod() throws UnsupportedEncodingException {
        assertEquals(comment, sut.getComment());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("Comment", comment));
        testParameters(expectedParams, sut);
    }
}
