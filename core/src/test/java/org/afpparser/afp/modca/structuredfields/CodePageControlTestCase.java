package org.afpparser.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.afpparser.afp.modca.Context;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.CPIRepeatingGroupLength;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Control;
import org.afpparser.afp.modca.structuredfields.control.CodePageControl;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CodePageControl}.
 */
public class CodePageControlTestCase extends StructuredFieldTestCase<CodePageControl> {

    private final String defaultChar = "TestChar";
    private CodePageControl sut;
    private CodePageControl noPresentation;
    private CodePageControl noIncrement;
    private CodePageControl doubleByte;
    private CodePageControl singleByteUnicode;
    private CodePageControl doubleByteUnicode;
    private CodePageControl enableVariableSpace;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Control.CPC);

        ByteBuffer bb = ByteBuffer.allocate(15);

        bb.put(defaultChar.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(1, 0x0A, 2, 3, 1));
        byte[] data = bb.array();
        Context context = new Context();
        sut = new CodePageControl(intro, new Parameters(data, "Cp500"), context);
        setMembers(sut, intro);

        data[8] = 0x02;
        noPresentation = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[8] = 0x04;
        noIncrement = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[9] = 0x0B;
        doubleByte = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[9] = (byte) 0xFE;
        singleByteUnicode = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[9] = (byte) 0xFF;
        doubleByteUnicode = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[12] = (byte) 0x10;
        enableVariableSpace = new CodePageControl(intro, new Parameters(data, "Cp500"), context);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(defaultChar, sut.getDefaultGCGID());
        assertTrue(sut.isInvalidCodedCharacter());
        assertFalse(sut.isNoPresensentation());
        assertFalse(sut.isNoIncrement());
        assertEquals(0x02, sut.getSpaceCharacterSectionNumber());
        assertEquals(0x03, sut.getSpaceCharacterCodePoint());
        assertTrue(sut.isAscendingCodePoint());
        assertFalse(sut.isVariableSpaceEnabled());

        assertTrue(noPresentation.isNoPresensentation());

        assertTrue(noIncrement.isNoIncrement());

        assertEquals(CPIRepeatingGroupLength.DOUBLE_BYTE, doubleByte.getCPIRepeatingGroupLength());

        assertEquals(CPIRepeatingGroupLength.SINGLE_BYTE_INC_UNICODE,
                singleByteUnicode.getCPIRepeatingGroupLength());

        assertEquals(CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE,
                doubleByteUnicode.getCPIRepeatingGroupLength());

        assertTrue(enableVariableSpace.isVariableSpaceEnabled());

    }
}
