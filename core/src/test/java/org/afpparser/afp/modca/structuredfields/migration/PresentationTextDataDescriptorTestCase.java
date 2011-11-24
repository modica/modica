package org.afpparser.afp.modca.structuredfields.migration;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

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
        sut = new PresentationTextDataDescriptor(intro, sfData);
        setMembers(sut, intro, Collections.<Triplet> emptyList());
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x0102, sut.getXptUnits());
        assertEquals(0x0304, sut.getYptUnits());
        // byte values 0x05 and 0x08 are missed
        assertEquals(0x0607, sut.getXptSize());
        assertEquals(0x090A, sut.getYptSize());
    }

}
