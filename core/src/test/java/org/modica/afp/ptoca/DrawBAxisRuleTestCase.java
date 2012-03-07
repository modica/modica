package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;
import org.modica.afp.ptoca.ControlSequenceIdentifier;
import org.modica.afp.ptoca.DrawBAxisRule;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link DrawBAxisRule}.
 */
public class DrawBAxisRuleTestCase extends ControlSequenceTestCase<DrawBAxisRule> {

    private DrawBAxisRule sutWithWidth;
    private DrawBAxisRule sutWithoutWidth;

    private final byte[] dataArray = ByteUtils.createByteArray(1, 2, 3, 4, 5);

    @Before
    public void setUp() {
        Parameters params = new Parameters(dataArray, "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.DRAW_B_AXIS_RULE;
        int length = 7;
        boolean isChained = true;

        sutWithWidth = new DrawBAxisRule(expectedCsId, length, isChained, params);
        setMembers(sutWithWidth, expectedCsId, isChained, length);

        sutWithoutWidth = new DrawBAxisRule(expectedCsId, 4, isChained,
                new Parameters(dataArray, "Cp500"));
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, sutWithWidth.getDrawLength());
        assertEquals(772.01953125, sutWithWidth.getDrawWidth(), 0.0000001);

        assertEquals(0x102, sutWithoutWidth.getDrawLength());
        assertEquals(0, (int) sutWithoutWidth.getDrawWidth());

        assertEquals("length=258 width=772.01953125", sutWithWidth.getValueAsString());
        assertEquals("length=258 width=0.0", sutWithoutWidth.getValueAsString());
    }
}
