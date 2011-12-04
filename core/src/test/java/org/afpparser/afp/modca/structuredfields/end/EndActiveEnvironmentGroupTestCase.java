package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

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

    @Before
    public void setUp() throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.EAG);
        Parameters params = new Parameters("TestStri".getBytes("Cp500"));
        sut = new EndActiveEnvironmentGroup(intro, params);

        Parameters matchesAnyParams = new Parameters(ByteUtils.createByteArray(0xff, 0xff));
        sutMatchesAny = new EndActiveEnvironmentGroup(intro, matchesAnyParams);

        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals("TestStri", sut.getAegName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getAegName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }
}
