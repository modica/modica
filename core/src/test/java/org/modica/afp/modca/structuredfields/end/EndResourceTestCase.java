package org.modica.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.SfTypeFactory.End;
import org.modica.afp.modca.structuredfields.end.EndResource;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link EndResource}.
 */
public class EndResourceTestCase extends StructuredFieldTestCase<EndResource> {

    private EndResource sut;
    private EndResource sutMatchesAny;
    private final String resourceName = "Resource";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(End.ERS);

        Parameters params = new Parameters(resourceName.getBytes("Cp500"));
        Parameters matchesAny = new Parameters(ByteUtils.createByteArray(0xff, 0xff));
        sut = new EndResource(intro, params);
        sutMatchesAny = new EndResource(intro, matchesAny);
        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(resourceName, sut.getRSName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getRSName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ResourceName", resourceName));
        testParameters(expectedParams, sut);
    }
}
