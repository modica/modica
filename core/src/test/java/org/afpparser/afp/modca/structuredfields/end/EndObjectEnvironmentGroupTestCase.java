package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link EndObjectEnvironmentGroup}.
 */
public class EndObjectEnvironmentGroupTestCase extends
        StructuredFieldTestCase<EndObjectEnvironmentGroup> {

    private EndObjectEnvironmentGroup sut;
    private EndObjectEnvironmentGroup sutMatchesAny;
    private final String oegName = "Blahblah";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.EOG);
        Parameters params = new Parameters(oegName.getBytes("Cp500"), "Cp500");
        sut = new EndObjectEnvironmentGroup(intro, params);

        Parameters matchesAny = new Parameters(ByteUtils.createByteArray(0xff, 0xff), "Cp500");
        sutMatchesAny = new EndObjectEnvironmentGroup(intro, matchesAny);

        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(oegName, sut.getOegName());
        assertEquals(false, sut.nameMatchesAny());
        assertEquals(null, sutMatchesAny.getOegName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ObjectEnvironmentGroupName", oegName));
        testParameters(expectedParams, sut);
    }
}
