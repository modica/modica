package org.afpparser.afp.modca.structuredfields.migration;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
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
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Migration.PTD);

        byte[] sfData = ByteUtils.createByteArray(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Parameters params = new Parameters(sfData);
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

}
