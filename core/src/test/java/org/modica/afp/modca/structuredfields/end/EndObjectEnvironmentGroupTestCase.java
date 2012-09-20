package org.modica.afp.modca.structuredfields.end;

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
import org.modica.afp.modca.structuredfields.end.EndObjectEnvironmentGroup;
import org.modica.afp.modca.structuredfields.types.EndType;
import org.modica.common.ByteUtils;

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
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(EndType.EOG);
        Parameters params = new Parameters(oegName.getBytes("Cp500"));
        sut = new EndObjectEnvironmentGroup(intro, params);

        Parameters matchesAny = new Parameters(ByteUtils.createByteArray(0xff, 0xff));
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
