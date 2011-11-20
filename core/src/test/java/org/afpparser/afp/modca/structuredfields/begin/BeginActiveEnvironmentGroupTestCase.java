package org.afpparser.afp.modca.structuredfields.begin;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletTestCase;
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

        List<Triplet> triplets = addTripletToList(FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF, FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        sut = new BeginActiveEnvironmentGroup(intro, triplets, aegName.getBytes("Cp500"));
        super.setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetAegName() {
        assertEquals(sut.getAegName(), aegName);
    }

}
