package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link EndResource}.
 */
public class EndResourceTestCase extends StructuredFieldTestCase<EndResource> {

    private EndResource sut;
    private EndResource sutMatchesAny;
    private final String resourceName = "Resource";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.ERS);

        Parameters params = new Parameters(resourceName.getBytes("Cp500"), "Cp500");
        Parameters matchesAny = new Parameters(ByteUtils.createByteArray(0xff, 0xff), "Cp500");
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
}
