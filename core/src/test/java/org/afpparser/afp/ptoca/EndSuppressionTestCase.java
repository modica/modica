package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link EndSuppression}.
 */
public class EndSuppressionTestCase extends ControlSequenceTestCase<EndSuppression> {
    private EndSuppression sut;
    private final byte suppressionId = (byte) 0xFF;

    @Before
    public void setUp() {
        Parameters params = new Parameters(new byte[] { suppressionId }, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.END_SUPPRESSION;
        int length = 4;
        boolean isChained = true;

        sut = new EndSuppression(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(suppressionId, sut.getLid());

        assertEquals("lid=0xff", sut.getValueAsString());
    }
}
