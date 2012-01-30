package org.afpparser.afp.modca.structuredfields.data;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.afp.ptoca.AbsoluteMoveBaseline;
import org.afpparser.afp.ptoca.ControlSequence;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link PresentationTextData}.
 */
public class PresentationTextDataTestCase extends StructuredFieldTestCase<PresentationTextData> {

    private PresentationTextData sut;
    private final String controlSequence = "2BD304D30370";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Begin.BPT);

        Parameters params = new Parameters(ByteUtils.hexToBytes(controlSequence), "Cp500");
        sut = new PresentationTextData(intro, params);
        setMembers(sut, intro);
    }

    @Test
    public void testGetText() throws UnsupportedEncodingException {
        List<ControlSequence> cs = sut.getPtocaData();
        assertEquals(1, cs.size());
        AbsoluteMoveBaseline amb = (AbsoluteMoveBaseline) cs.get(0);
        assertEquals(880, amb.getDisplacement());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> params = sut.getParameters();
        assertEquals(0, params.size());
    }
}
