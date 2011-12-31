package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link EndCodePage}.
 */
public class EndCodePageTestCase extends StructuredFieldTestCase<EndCodePage> {

    private EndCodePage sut;
    private EndCodePage sutMatchesAny;
    private final String cpName = "CodePage";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.ECP);

        Parameters params = new Parameters(cpName.getBytes("Cp500"), "Cp500");
        sut = new EndCodePage(intro, params);
        Parameters matchesAnyParams = new Parameters(ByteUtils.createByteArray(0xff, 0xff), "Cp500");
        sutMatchesAny = new EndCodePage(intro, matchesAnyParams);
        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(cpName, sut.getCodePageName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getCodePageName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("CodePageName", cpName);
        testParameters(expectedParams, sut);
    }
}
