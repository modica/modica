package org.afpparser.afp.modca;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.afpparser.afp.modca.triplets.Triplet;
import org.junit.Test;

/**
 * A test case for {@link StructuredFieldWithTriplets}.
 *
 * @param <T> a sublclass of {@link StructuredFieldWithTriplets}
 */
public abstract class StructuredFieldWithTripletTestCase<T extends StructuredFieldWithTriplets>
        extends StructuredFieldTestCase<T> {
    private List<Triplet> triplets;
    private StructuredFieldWithTriplets sut;

    public void setMembers(StructuredFieldWithTriplets sut, SfIntroducer intro,
            List<Triplet> triplets) {
        super.setMemebers(sut, intro);
        this.triplets = triplets;
        this.sut = sut;
    }

    /**
     * Test the hasTriplets() method depending on the system under test.
     */
    public abstract void testHasTriplets();

    @Test
    public void testTripletList() {
        assertTrue(triplets.equals(sut.getTriplets()));

        try {
            sut.getTriplets().remove(1);
            fail("This list should be unmodifiable");
        } catch (UnsupportedOperationException e) {
            // Pass
        }
    }
}
