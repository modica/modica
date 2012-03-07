package org.modica.afp.modca.structuredfields.begin;

import static org.junit.Assert.assertEquals;

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
import org.modica.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.modica.afp.modca.structuredfields.begin.BeginResourceGroup;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;

/**
 * Test case for {@link BeginResourceGroup}.
 */
public class BeginResourceGroupTestCase extends
        StructuredFieldWithTripletsTestCase<BeginResourceGroup> {

    private BeginResourceGroup sut;
    private static final String resGroupName = "TestName";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Begin.BRG);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(resGroupName.getBytes("Cp500"), "Cp500");
        sut = new BeginResourceGroup(intro, triplets, params);
        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethod() {
        assertEquals(resGroupName, sut.getResourceGroupName());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ResourceGroupName", resGroupName));
        testParameters(expectedParams, sut);
    }
}
