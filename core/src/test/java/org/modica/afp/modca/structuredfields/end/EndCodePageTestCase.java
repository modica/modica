package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.End;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link EndCodePage}.
 */
public class EndCodePageTestCase extends StructuredFieldTestCase<EndCodePage> {

    private EndCodePage sut;
    private EndCodePage sutMatchesAny;
    private final String cpName = "CodePage";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(End.ECP);

        Parameters params = new Parameters(cpName.getBytes("Cp500"), "Cp500");
        sut = new EndCodePage(intro, params, new Context());
        Parameters matchesAnyParams = new Parameters(ByteUtils.createByteArray(0xff, 0xff), "Cp500");
        sutMatchesAny = new EndCodePage(intro, matchesAnyParams, new Context());
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
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("CodePageName", cpName));
        testParameters(expectedParams, sut);
    }
}
