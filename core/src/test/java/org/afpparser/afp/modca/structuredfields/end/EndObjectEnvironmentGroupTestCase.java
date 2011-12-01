package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
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

    @Before
    public void setUp() throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.EOG);
        sut = new EndObjectEnvironmentGroup(intro, "TestStri".getBytes("Cp500"));

        sutMatchesAny = new EndObjectEnvironmentGroup(intro, ByteUtils.createByteArray(0xff, 0xff));

        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals("TestStri", sut.getOegName());
        assertEquals(false, sut.nameMatchesAny());
        assertEquals(null, sutMatchesAny.getOegName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }
}
