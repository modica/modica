package org.afpparser.afp.modca.structuredfields.descriptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.List;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.foca.FontWeightClass;
import org.afpparser.afp.modca.foca.FontWidthClass;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Descriptor;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link FontDescriptor}.
 */
public class FontDescriptorTestCase extends StructuredFieldWithTripletsTestCase<FontDescriptor> {

    private FontDescriptor sut;
    private String description = "Typeface descriptor has 32 chars";
    private SfIntroducer intro;
    byte[] sutBytes;

    @Before
    public void setUp() throws UnsupportedEncodingException, MalformedURLException {
        intro = SfIntroducerTestCase.createGenericIntroducer(Descriptor.FND);

        ByteBuffer bb = ByteBuffer.allocate(80);
        byte[] descBytes = description.getBytes("Cp500");

        bb.put(descBytes);
        bb.put(ByteUtils.createByteArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                15, 16, 17));
        bb.put(new byte[15]);
        bb.put(ByteUtils.createByteArray(0xD8)); // Font Design flags
        bb.put(new byte[11]);
        bb.put(ByteUtils.createByteArray(1, 2, 3, 4));
        sutBytes = bb.array();

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);
        sut = new FontDescriptor(intro, triplets, new Parameters(sutBytes, "Cp500"));

        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(description, sut.getTypefaceDescription());
        assertEquals(FontWeightClass.ULTRALIGHT, sut.getFontWeight());
        assertEquals(FontWidthClass.EXTRACONDENSED, sut.getFontWidth());

        assertEquals(0x304, sut.getMaximumVerticalFontSize());
        assertEquals(0x506, sut.getNominalVerticalFontSize());
        assertEquals(0x708, sut.getMinimumVerticalFontSize());

        assertEquals(0x90A, sut.getMaximumHorizontalFontSize());
        assertEquals(0xB0C, sut.getNominalHorizontalFontSize());
        assertEquals(0xD0E, sut.getMinimumHorizontalFontSize());

        assertEquals(15, sut.getDesignGeneralClass());
        assertEquals(16, sut.getDesignSubClass());
        assertEquals(17, sut.getDesignSpecificClass());

        assertTrue(sut.isItalic());
        assertTrue(sut.hasUnderscore());
        assertTrue(sut.isHollow());
        assertTrue(sut.isOverstruck());

        assertEquals(0x102, sut.getGcsgid());
        assertEquals(0x304, sut.getFgid());
    }

    @Test
    public void testFontDesignFlags() throws UnsupportedEncodingException, MalformedURLException {
        sutBytes[64] = 0;
        sut = createdescriptor();
        assertFalse(sut.isItalic());
        assertFalse(sut.hasUnderscore());
        assertFalse(sut.isHollow());
        assertFalse(sut.isOverstruck());

        sutBytes[64] = (byte) 0x80;
        sut = createdescriptor();
        assertTrue(sut.isItalic());
        assertFalse(sut.hasUnderscore());
        assertFalse(sut.isHollow());
        assertFalse(sut.isOverstruck());

        sutBytes[64] = 0x40;
        sut = createdescriptor();
        assertFalse(sut.isItalic());
        assertTrue(sut.hasUnderscore());
        assertFalse(sut.isHollow());
        assertFalse(sut.isOverstruck());

        sutBytes[64] = 0x10;
        sut = createdescriptor();
        assertFalse(sut.isItalic());
        assertFalse(sut.hasUnderscore());
        assertTrue(sut.isHollow());
        assertFalse(sut.isOverstruck());

        sutBytes[64] = 8;
        sut = createdescriptor();
        assertFalse(sut.isItalic());
        assertFalse(sut.hasUnderscore());
        assertFalse(sut.isHollow());
        assertTrue(sut.isOverstruck());
    }

    private FontDescriptor createdescriptor() throws UnsupportedEncodingException,
            MalformedURLException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Descriptor.FND);
        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);
        return new FontDescriptor(intro, triplets, new Parameters(sutBytes, "Cp500"));
    }
}
