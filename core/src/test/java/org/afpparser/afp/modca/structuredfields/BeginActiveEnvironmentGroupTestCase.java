package org.afpparser.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.SfIntroducerTestCase;
import org.afpparser.afp.modca.StructuredFieldWithTripletTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.begin.BeginActiveEnvironmentGroup;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * TestCase for {@link BeginActiveEnvironmentGroup}.
 */
public class BeginActiveEnvironmentGroupTestCase extends
        StructuredFieldWithTripletTestCase<BeginActiveEnvironmentGroup> {
    private BeginActiveEnvironmentGroup sut;
    private SfIntroducer intro;
    private final String aegName = "TESTNAME";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = SfIntroducerTestCase.createGenericIntroducer(Begin.BAG);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        sut = new BeginActiveEnvironmentGroup(intro, triplets, aegName.getBytes("Cp500"));
        super.setMembers(sut, intro, triplets);
    }

    public List<Triplet> addTripletToList(String... tripletStr) throws MalformedURLException,
            UnsupportedEncodingException {
        List<Triplet> triplets = new ArrayList<Triplet>();
        for (String str : tripletStr) {
            triplets.add(FullyQualifiedNameTestCase.createFQN(str));
        }
        return triplets;
    }

    @Test
    @Override
    public void testHasTriplets() {
        assertTrue(sut.hasTriplets());
    }

    @Test
    public void testGetAegName() {
        assertEquals(sut.getAegName(), aegName );
    }

}
