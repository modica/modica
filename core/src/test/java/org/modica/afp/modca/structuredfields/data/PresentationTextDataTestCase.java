package org.modica.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.ptoca.AbsoluteMoveBaseline;
import org.modica.afp.ptoca.ControlSequence;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link PresentationTextData}.
 */
public class PresentationTextDataTestCase extends StructuredFieldTestCase<PresentationTextData> {

    private PresentationTextData sut;
    private final String controlSequence = "2BD304D30370";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Begin.BPT);

        Parameters params = new Parameters(ByteUtils.hexToBytes(controlSequence), "Cp500");
        sut = new PresentationTextData(intro, params, new Context());
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
