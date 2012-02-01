package org.afpparser.afp.modca.structuredfields.migration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Migration;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
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
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Migration.PTD);

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
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("X-AxisUnits", 0x102));
        expectedParams.add(new ParameterAsString("Y-AxisUnits", 0x304));
        expectedParams.add(new ParameterAsString("X-AxisSize", 0x50607));
        expectedParams.add(new ParameterAsString("Y-AxisSize", 0x8090A));
        testParameters(expectedParams, sut);
    }
}
