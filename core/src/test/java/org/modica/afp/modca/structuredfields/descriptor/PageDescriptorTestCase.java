package org.modica.afp.modca.structuredfields.descriptor;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.PresentationSpaceUnits;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.structuredfields.descriptor.PageDescriptor;
import org.modica.afp.modca.structuredfields.types.DescriptorType;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link PageDescriptor}
 */
public class PageDescriptorTestCase extends StructuredFieldWithTripletsTestCase<PageDescriptor> {

    private PageDescriptor sut;
    private PageDescriptor cmSut;
    private StructuredFieldIntroducer intro;

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(DescriptorType.PGD);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        byte[] bytes = ByteUtils.createByteArray(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0);

        Parameters inchesParams = new Parameters(bytes);
        sut = new PageDescriptor(intro, triplets, inchesParams);
        super.setMembers(sut, intro, triplets);
        bytes[0] = 1;
        bytes[1] = 1;
        Parameters cmParams = new Parameters(bytes);
        cmSut = new PageDescriptor(intro, triplets, cmParams);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x0102, sut.getXpgUnit());
        assertEquals(0x0304, sut.getYpgUnit());
        assertEquals(0x050607, sut.getXpgSize());
        assertEquals(0x08090A, sut.getYpgSize());
        assertEquals(PresentationSpaceUnits.INCHES_10, sut.getXpgBase());
        assertEquals(PresentationSpaceUnits.INCHES_10, sut.getYpgBase());

        assertEquals(0x0102, cmSut.getXpgUnit());
        assertEquals(0x0304, cmSut.getYpgUnit());
        assertEquals(0x050607, cmSut.getXpgSize());
        assertEquals(0x08090A, cmSut.getYpgSize());
        assertEquals(PresentationSpaceUnits.CENTIMETRE_10, cmSut.getXpgBase());
        assertEquals(PresentationSpaceUnits.CENTIMETRE_10, cmSut.getYpgBase());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("X-AxisBaseUnit", PresentationSpaceUnits.INCHES_10));
        expectedParams.add(new ParameterAsString("Y-AxisBaseUnit", PresentationSpaceUnits.INCHES_10));
        expectedParams.add(new ParameterAsString("X-AxisPageUnit", 0x102));
        expectedParams.add(new ParameterAsString("Y-AxisPageUnit", 0x304));
        expectedParams.add(new ParameterAsString("X-AxisPageSize", 0x50607));
        expectedParams.add(new ParameterAsString("Y-AxisPageSize", 0x8090A));
        testParameters(expectedParams, sut);

        expectedParams.set(0, new ParameterAsString("X-AxisBaseUnit",
                PresentationSpaceUnits.CENTIMETRE_10));
        expectedParams.set(1, new ParameterAsString("Y-AxisBaseUnit",
                PresentationSpaceUnits.CENTIMETRE_10));
        assertEquals(PresentationSpaceUnits.CENTIMETRE_10, cmSut.getYpgBase());
        testParameters(expectedParams, cmSut);
    }
}
