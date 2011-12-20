package org.afpparser.afp.modca.structuredfields.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Context;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.CPIRepeatingGroupLength;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Control;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CodePageControl}.
 */
public class CodePageControlTestCase extends StructuredFieldTestCase<CodePageControl> {

    private final String defaultChar = "TestChar";
    private CodePageControl sut;
    private CodePageControl notInvalid;
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
        bb.put(ByteUtils.createByteArray(0x80, 0x0A, 2, 3, 0x90));
        byte[] data = bb.array();
        Context context = new Context();
        sut = new CodePageControl(intro, new Parameters(data, "Cp500"), context);
        setMembers(sut, intro);

        data[8] = (byte) 0x00;
        notInvalid = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[8] = (byte) 0x40;
        noPresentation = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[8] = 0x60;
        noIncrement = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[9] = 0x0B;
        doubleByte = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[9] = (byte) 0xFE;
        singleByteUnicode = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[9] = (byte) 0xFF;
        doubleByteUnicode = new CodePageControl(intro, new Parameters(data, "Cp500"), context);

        data[12] = (byte) 0x08;
        enableVariableSpace = new CodePageControl(intro, new Parameters(data, "Cp500"), context);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(defaultChar, sut.getDefaultGCGID());
        assertTrue(sut.isInvalidCodedCharacter());
        assertFalse(sut.isNoPresensentation());
        assertFalse(sut.isNoIncrement());
        assertEquals(CPIRepeatingGroupLength.SINGLE_BYTE, sut.getCPIRepeatingGroupLength());
        assertEquals(0x02, sut.getSpaceCharacterSectionNumber());
        assertEquals(0x03, sut.getSpaceCharacterCodePoint());
        assertTrue(sut.isAscendingCodePoint());
        assertFalse(sut.isVariableSpaceEnabled());

        assertFalse(notInvalid.isInvalidCodedCharacter());

        assertTrue(noPresentation.isNoPresensentation());

        assertTrue(noIncrement.isNoIncrement());

        assertEquals(CPIRepeatingGroupLength.DOUBLE_BYTE, doubleByte.getCPIRepeatingGroupLength());

        assertEquals(CPIRepeatingGroupLength.SINGLE_BYTE_INC_UNICODE,
                singleByteUnicode.getCPIRepeatingGroupLength());

        assertEquals(CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE,
                doubleByteUnicode.getCPIRepeatingGroupLength());

        assertTrue(enableVariableSpace.isVariableSpaceEnabled());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("DefaultGCGID", defaultChar);
        expectedParams.put("isInvalidCodedCharacter", String.valueOf(true));
        expectedParams.put("isNoPresentation", String.valueOf(false));
        expectedParams.put("isNoIncrement", String.valueOf(false));
        expectedParams.put("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.SINGLE_BYTE.toString());
        expectedParams.put("SpaceCharSectionNumber", String.valueOf(2));
        expectedParams.put("SpaceCharCodePoint", String.valueOf(3));
        expectedParams.put("isAscendingCodePoint", String.valueOf(true));
        expectedParams.put("isVariableSpaceEnabled", String.valueOf(false));
        expectedParams.put("DefaultUnicodeValue", String.valueOf(0));
        testParameters(expectedParams, sut);

        expectedParams.put("isInvalidCodedCharacter", String.valueOf(false));
        testParameters(expectedParams, notInvalid);

        expectedParams.put("isNoPresentation", String.valueOf(true));
        testParameters(expectedParams, noPresentation);

        expectedParams.put("isNoIncrement", String.valueOf(true));
        testParameters(expectedParams, noIncrement);

        expectedParams.put("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.DOUBLE_BYTE.toString());
        testParameters(expectedParams, doubleByte);

        expectedParams.put("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.SINGLE_BYTE_INC_UNICODE.toString());
        testParameters(expectedParams, singleByteUnicode);

        expectedParams.put("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE.toString());
        testParameters(expectedParams, doubleByteUnicode);
    }
}
