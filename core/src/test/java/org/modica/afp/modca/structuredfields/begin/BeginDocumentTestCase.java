package org.modica.afp.modca.structuredfields.begin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import org.modica.afp.modca.structuredfields.begin.BeginDocument;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link BeginDocument}.
 */
public class BeginDocumentTestCase extends StructuredFieldWithTripletsTestCase<BeginDocument> {

    private BeginDocument sut;
    private BeginDocument sutWithoutName;
    private StructuredFieldIntroducer intro;
    private final String docName = "TESTNAME";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Begin.BDT);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(docName.getBytes("Cp500"), "Cp500");
        sut = new BeginDocument(intro, triplets, params);
        super.setMembers(sut, intro, triplets);

        params = new Parameters(ByteUtils.createByteArray(0xff, 0xff, 0x01, 0x02), "Cp500");
        sutWithoutName = new BeginDocument(intro, triplets, params);
    }

    @Test
    public void testGetters() {
        assertFalse(sut.docNameProvidedBySystem());
        assertEquals(docName, sut.getDocName());

        assertTrue(sutWithoutName.docNameProvidedBySystem());
        assertNull(sutWithoutName.getDocName());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("DocumentName", docName));
        testParameters(expectedParams, sut);
    }
}
