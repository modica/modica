package org.modica.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.ParameterAsStringTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.modica.common.ByteUtils;

/**
 * A test case for {@link StructuredFieldWithTriplets}.
 *
 * @param <T> a sublclass of {@link StructuredFieldWithTriplets}
 */
public abstract class StructuredFieldWithTripletsTestCase<T extends StructuredFieldWithTriplets>
        extends StructuredFieldTestCase<T> {
    private List<Triplet> triplets;
    private T sut;

    public final void setMembers(T sut, StructuredFieldIntroducer intro,
            List<Triplet> triplets) {
        super.setMembers(sut, intro);
        this.triplets = triplets;
        this.sut = sut;
    }

    /**
     * Test the hasTriplets() method depending on the system under test.
     */
    public final void testHasTriplets() {
        assertTrue(sut.hasTriplets());
    };

    public static List<Triplet> addTripletToList(String... tripletStr) throws MalformedURLException,
            UnsupportedEncodingException {
        List<Triplet> triplets = new ArrayList<Triplet>();
        for (String str : tripletStr) {
            triplets.add(FullyQualifiedNameTestCase.createFQN(ByteUtils.hexToBytes(str)));
        }
        return triplets;
    }

    @Test
    public final void testTripletList() {
        assertTrue(triplets.equals(sut.getTriplets()));

        try {
            sut.getTriplets().remove(1);
            fail("This list should be unmodifiable");
        } catch (UnsupportedOperationException e) {
            // Pass
        }
    }

    @Test
    public final void testGetTripletsAsStrings() {
        assertEquals(triplets.size(), sut.getTripletParameters().size());
        for (int i = 0; i < sut.getTripletParameters().size(); i++) {
            List<ParameterAsString> tripletParams = sut.getTripletParameters().get(i);
            ParameterAsStringTestCase.testParameters(tripletParams, triplets.get(i).getParameters());
        }
    }
}
