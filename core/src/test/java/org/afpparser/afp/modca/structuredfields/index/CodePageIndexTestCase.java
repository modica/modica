package org.afpparser.afp.modca.structuredfields.index;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

import org.afpparser.afp.modca.Context;
import org.afpparser.afp.modca.Context.FOCAContext;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.CPIRepeatingGroupLength;
import org.afpparser.afp.modca.common.GraphicalCharacterUseFlags;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Index;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.afp.modca.structuredfields.index.CodePageIndex.CPI;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CodePageIndex}.
 */
public class CodePageIndexTestCase extends StructuredFieldTestCase<CodePageIndex> {

    private CodePageIndex sut;

    private CodePageIndex doubleByteUnicodeSut;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Index.CPI);

        Context context = new Context();
        context.put(FOCAContext.CPI_REPEATING_GROUP_LENGTH, CPIRepeatingGroupLength.SINGLE_BYTE);
        sut = createSingleByteCPI(CPIRepeatingGroupLength.SINGLE_BYTE);
        doubleByteUnicodeSut = createDoubleByteCPI(CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE);

        setMembers(sut, intro);
    }

    private CodePageIndex createSingleByteCPI(CPIRepeatingGroupLength cpiRLen)
            throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Index.CPI);
        ByteBuffer bb = ByteBuffer.allocate(30);
        bb.put("TestChar".getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(1, 4));
        bb.put("TestCTwo".getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(2, 5));
        bb.put("TestTree".getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(4, 6));
        Context context = new Context();
        context.put(FOCAContext.CPI_REPEATING_GROUP_LENGTH, cpiRLen);
        return new CodePageIndex(intro, new Parameters(bb.array()), context);
    }

    private CodePageIndex createDoubleByteCPI(CPIRepeatingGroupLength cpiRLen)
            throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Index.CPI);
        ByteBuffer bb = ByteBuffer.allocate(42);
        bb.put("TestChar".getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(1, 2, 3, 1, 5));
        bb.put("TestCTwo".getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(2, 3, 4, 2, 6, 7));
        bb.put("TestTree".getBytes("Cp500"));
        bb.put(ByteUtils.createByteArray(4, 1, 2, 3, 1, 2, 3));
        Context context = new Context();
        context.put(FOCAContext.CPI_REPEATING_GROUP_LENGTH, cpiRLen);
        return new CodePageIndex(intro, new Parameters(bb.array()), context);
    }

    @Test
    public void testGetterMethods() {
        List<CPI> singleByteCPIs = sut.getCodePageIndices();
        testCPI(singleByteCPIs.get(0), "TestChar", 4, 0, (byte) 1);
        testCPI(singleByteCPIs.get(1), "TestCTwo", 5, 0, (byte) 2);
        testCPI(singleByteCPIs.get(2), "TestTree", 6, 0, (byte) 4);

        List<CPI> doubleByteCPIs = doubleByteUnicodeSut.getCodePageIndices();
        testCPI(doubleByteCPIs.get(0), "TestChar", 0x203, 5, (byte) 1);
        testCPI(doubleByteCPIs.get(1), "TestCTwo", 0x304, 0x607, (byte) 2);
        testCPI(doubleByteCPIs.get(2), "TestTree", 0x102, 0x10203, (byte) 4);
    }

    private void testCPI(CPI cpi, String gcgid, int codePoint, int unicode, byte flag) {
        assertEquals(gcgid, cpi.getGcgid());
        assertEquals(codePoint, cpi.getCodePoint());
        assertEquals(unicode, cpi.getUnicodeIndex());
        assertEquals(GraphicalCharacterUseFlags.isInvalidCodedCharacter(flag),
                cpi.isInvalidCodedCharacter());
        assertEquals(GraphicalCharacterUseFlags.isNoPresentation(flag), cpi.isNoPresentation());
        assertEquals(GraphicalCharacterUseFlags.isNoIncrement(flag), cpi.isNoIncrement());
    }
}
