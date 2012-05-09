package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link BeginFont}.
 */
public class BeginFontTestCase extends StructuredFieldWithTripletsTestCase<BeginFont> {
    private BeginFont sut;
    private StructuredFieldIntroducer intro;
    private final String charsetName = "Charsets";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Begin.BFN);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(charsetName.getBytes("Cp500"), "Cp500");
        sut = new BeginFont(intro, triplets, params);
        super.setMembers(sut, intro, triplets);
    }

    @Test
    public void testDocName() {
        assertEquals(charsetName, sut.getCharacterSetName());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("CharactersetName", charsetName));
        testParameters(expectedParams, sut);
    }
}
