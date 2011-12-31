package org.afpparser.afp.modca.structuredfields.begin;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link BeginObjectEnvironmentGroup}.
 */
public class BeginObjectEnvironmentGroupTestCase extends
        StructuredFieldWithTripletsTestCase<BeginObjectEnvironmentGroup> {

    private BeginObjectEnvironmentGroup sut;
    private static final String oegName = "TestName";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Begin.BOG);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(oegName.getBytes("Cp500"), "Cp500");
        sut = new BeginObjectEnvironmentGroup(intro, triplets, params);
        super.setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(oegName, sut.getOegName());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("ObjectEnvironmentGroupName", oegName);
        testParameters(expectedParams, sut);
    }
}
