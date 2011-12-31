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
 * Test case for
 */
public class BeginPageTestCase extends StructuredFieldWithTripletsTestCase<BeginPage> {

    private BeginPage sut;
    private StructuredFieldIntroducer intro;
    private final String pageName = "TESTNAME";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = SfIntroducerTestCase.createGenericIntroducer(Begin.BPG);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(pageName.getBytes("Cp500"), "Cp500");
        sut = new BeginPage(intro, triplets, params);
        super.setMembers(sut, intro, triplets);
    }

    @Test
    public void testDocName() {
        assertEquals(pageName, sut.getPageName());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("PageName", pageName);
        testParameters(expectedParams, sut);
    }
}
