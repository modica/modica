package org.modica.afp.modca.structuredfields.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.CPIRepeatingGroupLength;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Control;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.common.ByteUtils;

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
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Control.CPC);

        ByteBuffer bb = ByteBuffer.allocate(15);

        bb.put(defaultChar.getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(0x80, 0x0A, 2, 3, 0x90));
        byte[] data = bb.array();
        Context context = new ContextImpl();
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

        assertEquals(0, singleByteUnicode.getDefaultUnicodeIndex());
        assertEquals(0, doubleByteUnicode.getDefaultUnicodeIndex());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("DefaultGCGID", defaultChar));
        expectedParams.add(new ParameterAsString("isInvalidCodedCharacter", true));
        expectedParams.add(new ParameterAsString("isNoPresentation", false));
        expectedParams.add(new ParameterAsString("isNoIncrement", false));
        expectedParams.add(new ParameterAsString("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.SINGLE_BYTE));
        expectedParams.add(new ParameterAsString("SpaceCharSectionNumber", 2));
        expectedParams.add(new ParameterAsString("SpaceCharCodePoint", 3));
        expectedParams.add(new ParameterAsString("isAscendingCodePoint", true));
        expectedParams.add(new ParameterAsString("isVariableSpaceEnabled", false));
        expectedParams.add(new ParameterAsString("DefaultUnicodeValue", 0));
        testParameters(expectedParams, sut);

        expectedParams.set(1, new ParameterAsString("isInvalidCodedCharacter", false));
        testParameters(expectedParams, notInvalid);

        expectedParams.set(2, new ParameterAsString("isNoPresentation", true));
        testParameters(expectedParams, noPresentation);

        expectedParams.set(3, new ParameterAsString("isNoIncrement", true));
        testParameters(expectedParams, noIncrement);

        expectedParams.set(4, new ParameterAsString("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.DOUBLE_BYTE));
        testParameters(expectedParams, doubleByte);

        expectedParams.set(4, new ParameterAsString("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.SINGLE_BYTE_INC_UNICODE));
        testParameters(expectedParams, singleByteUnicode);

        expectedParams.set(4, new ParameterAsString("CPIRepeatingGroupLength",
                CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE));
        testParameters(expectedParams, doubleByteUnicode);
    }
}
