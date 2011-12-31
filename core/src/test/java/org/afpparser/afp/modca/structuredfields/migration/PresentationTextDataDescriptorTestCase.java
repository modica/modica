package org.afpparser.afp.modca.structuredfields.migration;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Migration;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * A test case for {@link PresentationTextDataDescriptor}.
 */
public class PresentationTextDataDescriptorTestCase extends
        StructuredFieldWithTripletsTestCase<PresentationTextDataDescriptor> {

    private PresentationTextDataDescriptor sut;

    @Before
    public void setUp() {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Migration.PTD);

        byte[] sfData = ByteUtils.createByteArray(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0);
        Parameters params = new Parameters(sfData, "Cp500");
        sut = new PresentationTextDataDescriptor(intro, params);
        setMembers(sut, intro, Collections.<Triplet> emptyList());
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, sut.getXptUnits());
        assertEquals(0x304, sut.getYptUnits());
        assertEquals(0x50607, sut.getXptSize());
        assertEquals(0x8090A, sut.getYptSize());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("X-AxisUnits", String.valueOf(0x102));
        expectedParams.put("Y-AxisUnits", String.valueOf(0x304));
        expectedParams.put("X-AxisSize", String.valueOf(0x50607));
        expectedParams.put("Y-AxisSize", String.valueOf(0x8090A));
        testParameters(expectedParams, sut);
    }
}
