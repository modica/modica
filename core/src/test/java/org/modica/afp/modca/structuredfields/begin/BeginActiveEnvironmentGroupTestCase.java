package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.structuredfields.types.BeginType;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;

import static org.junit.Assert.assertEquals;

/**
 * TestCase for {@link BeginActiveEnvironmentGroup}.
 */
public class BeginActiveEnvironmentGroupTestCase extends
        StructuredFieldWithTripletsTestCase<BeginActiveEnvironmentGroup> {
    private BeginActiveEnvironmentGroup sut;
    private StructuredFieldIntroducer intro;
    private final String aegName = "TESTNAME";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(BeginType.BAG);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(aegName.getBytes("Cp500"));
        sut = new BeginActiveEnvironmentGroup(intro, triplets, params);
        super.setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetAegName() {
        assertEquals(sut.getAegName(), aegName);
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ActiveEnvironmentGroupName", aegName));
        testParameters(expectedParams, sut);
    }

}
