package org.afpparser.afp.modca;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link StructuredFieldWithTripletGroup}.
 */
public abstract class StructuredFieldTestCase<S extends AbstractStructuredField> {

    private AbstractStructuredField sut;
    private SfIntroducer intro;

    public void setMemebers(AbstractStructuredField sut, SfIntroducer intro) {
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

}
