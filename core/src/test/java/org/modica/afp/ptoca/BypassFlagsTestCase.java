package org.modica.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modica.afp.ptoca.BypassFlags;

/**
 * Test case for {@link BypassFlags}.
 */
public class BypassFlagsTestCase {

    @Test
    public void testContructorAndGetterMethods() {
        testGetterMethods(false, false, false, 0);
        testGetterMethods(true, false, false, 0x08);
        testGetterMethods(false, true, false, 0x04);
        testGetterMethods(false, false, true, 0x02);
        testGetterMethods(false, false, false, 0x01);
        // No bypass in effect overrides all the others
        testGetterMethods(false, false, false, 0x0F);
    }

    private void testGetterMethods(boolean bypassRMI, boolean bypassAMI, boolean bypassSpace,
            int id) {
        BypassFlags sut = new BypassFlags((byte) id);
        assertEquals(bypassRMI, sut.bypassRelativeMoveInline());
        assertEquals(bypassAMI, sut.bypassAbsoluteMoveInline());
        assertEquals(bypassSpace, sut.bypassSpaceChars());
    }
}
