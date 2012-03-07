package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.DrawIAxisRule;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link DrawIAxisRule}.
 */
public class DrawIAxisRuleTestCase extends ControlSequenceTestCase<DrawIAxisRule> {

    private DrawIAxisRule sutWithWidth;
    private DrawIAxisRule sutWithoutWidth;

    private final byte[] dataArray = ByteUtils.createByteArray(1, 2, 3, 4, 0x80);

    @Before
    public void setUp() {
        Parameters params = new Parameters(dataArray, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.DRAW_I_AXIS_RULE;
        int length = 7;
        boolean isChained = true;

        sutWithWidth = new DrawIAxisRule(expectedCsId, length, isChained, params);
        setMembers(sutWithWidth, expectedCsId, isChained, length);

        sutWithoutWidth = new DrawIAxisRule(expectedCsId, 4, isChained,
                new Parameters(dataArray, "Cp500"));
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, sutWithWidth.getDrawLength());
        assertEquals(772.5, sutWithWidth.getDrawWidth(), 0.0000001);

        assertEquals(0x102, sutWithoutWidth.getDrawLength());
        assertEquals(0, (int) sutWithoutWidth.getDrawWidth());

        assertEquals("length=258 width=772.5", sutWithWidth.getValueAsString());
        assertEquals("length=258 width=0.0", sutWithoutWidth.getValueAsString());
    }
}
