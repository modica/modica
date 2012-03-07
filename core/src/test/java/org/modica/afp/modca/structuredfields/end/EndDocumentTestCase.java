package org.modica.afp.modca.structuredfields.end;

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
import org.modica.afp.modca.structuredfields.SfTypeFactory.End;
import org.modica.afp.modca.structuredfields.end.EndDocument;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link EndDocument}.
 */
public class EndDocumentTestCase extends StructuredFieldWithTripletsTestCase<EndDocument> {

    private EndDocument sut;
    private EndDocument sutMatchesAny;
    private final String docName = "TestDocs";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(End.EDT);

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
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("DocumentName", docName));
        testParameters(expectedParams, sut);
    }
}
