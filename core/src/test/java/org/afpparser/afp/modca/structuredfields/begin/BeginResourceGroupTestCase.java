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
 * Test case for {@link BeginResourceGroup}.
 */
public class BeginResourceGroupTestCase extends
        StructuredFieldWithTripletsTestCase<BeginResourceGroup> {

    private BeginResourceGroup sut;
    private static final String resGroupName = "TestName";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Begin.BRG);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(resGroupName.getBytes("Cp500"), "Cp500");
        sut = new BeginResourceGroup(intro, triplets, params);
        setMembers(sut, intro, triplets);
    }

    public void testGetterMethod() {
        assertEquals(resGroupName, sut.getResourceGroupName());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("ResourceGroupName", resGroupName);
        testParameters(expectedParams, sut);
    }
}
