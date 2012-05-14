package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.End;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

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
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(End.EAG);
        Parameters params = new Parameters(aegName.getBytes("Cp500"));
        sut = new EndActiveEnvironmentGroup(intro, params);

        Parameters matchesAnyParams = new Parameters(ByteUtils.createByteArray(0xff, 0xff));
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
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ActiveEnvironmentGroupName", aegName));
        testParameters(expectedParams, sut);
    }
}
