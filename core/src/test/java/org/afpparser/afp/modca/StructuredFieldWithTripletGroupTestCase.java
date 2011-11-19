package org.afpparser.afp.modca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
import org.junit.Test;

/**
 * Test case for subclasses of {@link StructuredFieldWithTripletGroup}.
 *
 * @param <T> subclass of {@link StructuredFieldWithTripletGroup}
 */
public abstract class StructuredFieldWithTripletGroupTestCase<T extends StructuredFieldWithTripletGroup>
        extends StructuredFieldTestCase<T> {
    private StructuredFieldWithTripletGroup sut;
    private RepeatingTripletGroup rGroup;

    public void setMembers(StructuredFieldWithTripletGroup sut, SfIntroducer intro,
            RepeatingTripletGroup rGroup) {
        super.setMemebers(sut, intro);
        this.sut = sut;
        this.rGroup = rGroup;
    }

    @Override
    @Test
    public void testGetters() {
        assertEquals(rGroup, sut.getTripletGroup());
        assertEquals(rGroup.size() > 0, sut.hasTripletGroup());
    }
}
