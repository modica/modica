package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link EndDocument}.
 */
public class EndDocumentTestCase extends StructuredFieldWithTripletsTestCase<EndDocument> {

    private EndDocument sut;
    private EndDocument sutMatchesAny;
    private final String docName = "TestDocs";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(End.EDT);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(docName.getBytes("Cp500"), "Cp500");
        sut = new EndDocument(intro, triplets, params);
        Parameters matchesAnyParams = new Parameters(ByteUtils.createByteArray(0xff, 0xff), "Cp500");
        sutMatchesAny = new EndDocument(intro, triplets, matchesAnyParams);
        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(docName, sut.getDocName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getDocName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("DocumentName", docName);
        testParameters(expectedParams, sut);
    }
}
