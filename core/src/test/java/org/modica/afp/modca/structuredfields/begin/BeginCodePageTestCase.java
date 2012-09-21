package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.EbcdicStringHandler;
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
 * Test case for {@link BeginCodePage}.
 */
public class BeginCodePageTestCase extends StructuredFieldWithTripletsTestCase<BeginCodePage> {

    private BeginCodePage sut;
    private StructuredFieldIntroducer intro;
    private final String cfName = "codepage";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(BeginType.BCP);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(cfName.getBytes(EbcdicStringHandler.DEFAULT_CPGID_NAME));
        sut = new BeginCodePage(intro, triplets, params, new ContextImpl());
        super.setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(cfName, sut.getCFName());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("CodePageName", cfName));
        testParameters(expectedParams, sut);
    }
}
