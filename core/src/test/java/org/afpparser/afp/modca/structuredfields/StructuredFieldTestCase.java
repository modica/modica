package org.afpparser.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.ParameterAsStringTestCase;
import org.junit.Test;

/**
 * Test case for {@link StructuredFieldWithTripletGroup}.
 */
public abstract class StructuredFieldTestCase<S extends AbstractStructuredField> {

    private AbstractStructuredField sut;
    private StructuredFieldIntroducer intro;

    public void setMembers(AbstractStructuredField sut, StructuredFieldIntroducer intro) {
        this.sut = sut;
        this.intro = intro;
    }

    @Test
    public void testGetters() {
        assertEquals(intro.hasExtData(), sut.hasExtData());
        assertEquals(intro.hasSegmentedData(), sut.hasSegmentedData());
        assertEquals(intro.hasDataPadding(), sut.hasDataPadding());
        assertEquals(intro.getLength(), sut.getLength());
        assertEquals(intro.getType(), sut.getType());
        assertEquals(intro.getOffset(), sut.getOffset());
        assertEquals(intro.getExtLength(), sut.getExtLength());
    }

    public abstract void testGetParameters();

    public void testParameters(List<ParameterAsString> expectedParameters, StructuredField sf) {
        List<ParameterAsString> sutParams = sf.getParameters();
        ParameterAsStringTestCase.testParameters(expectedParameters, sutParams);
    }
}
