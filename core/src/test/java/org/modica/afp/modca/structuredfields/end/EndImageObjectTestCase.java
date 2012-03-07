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
import org.modica.afp.modca.structuredfields.end.EndImageObject;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.modica.common.ByteUtils;

/**
 * Test case for {@link EndImageObject}.
 */
public class EndImageObjectTestCase extends StructuredFieldWithTripletsTestCase<EndImageObject> {

    private EndImageObject sut;
    private EndImageObject sutMatchesAny;
    private final String idoName = "TesImage";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(End.EPT);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        Parameters params = new Parameters(idoName.getBytes("Cp500"), "Cp500");
        sut = new EndImageObject(intro, triplets, params);
        Parameters matchesAny = new Parameters(ByteUtils.createByteArray(0xff, 0xff), "Cp500");
        sutMatchesAny = new EndImageObject(intro, triplets, matchesAny);
        setMembers(sut, intro, triplets);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(idoName, sut.getIdoName());
        assertEquals(false, sut.nameMatchesAny());
        assertEquals(null, sutMatchesAny.getIdoName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ImageDataObjectName", idoName));
        testParameters(expectedParams, sut);
    }
}
