package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link EndActiveEnvironmentGroup}.
 */
public class EndActiveEnvironmentGroupTestCase extends
        StructuredFieldTestCase<EndActiveEnvironmentGroup> {
    private EndActiveEnvironmentGroup sut;
    private EndActiveEnvironmentGroup sutMatchesAny;
    private final String aegName = "TestStri";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.EAG);
        Parameters params = new Parameters(aegName.getBytes("Cp500"), "Cp500");
        sut = new EndActiveEnvironmentGroup(intro, params);

        Parameters matchesAnyParams = new Parameters(ByteUtils.createByteArray(0xff, 0xff), "Cp500");
        sutMatchesAny = new EndActiveEnvironmentGroup(intro, matchesAnyParams);

        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(aegName, sut.getAegName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getAegName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("ActiveEnvironmentGroupName", aegName);
        testParameters(expectedParams, sut);
    }
}
