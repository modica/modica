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
import org.modica.afp.modca.structuredfields.begin.BeginImageObject;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;

/**
 * Test case for {@link BeginImageObject}.
 */
public class BeginImageObjectTestCase extends StructuredFieldWithTripletsTestCase<BeginImageObject> {

    private BeginImageObject sut;
    private static final String idoName = "idonames";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Begin.BIM);
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Begin.BDT);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(idoName.getBytes("Cp500"), "Cp500");
        sut = new BeginImageObject(intro, triplets, params);
        super.setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(idoName, sut.getIdoName());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ImageObjectName", idoName));
        testParameters(expectedParams, sut);
    }
}
