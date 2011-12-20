package org.afpparser.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Map;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletGroupTestCase;
import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
import org.junit.Before;
import org.junit.Test;

/**
 * A test case for {@link MapCodedFont}.
 */
public class MapCodedFontTestCase extends StructuredFieldWithTripletGroupTestCase<MapCodedFont> {

    private MapCodedFont sut;

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Map.MCF);
        RepeatingTripletGroup tripletGroup = StructuredFieldWithTripletGroupTestCase.createGenericRepeatingGroup();

        sut = new MapCodedFont(intro, tripletGroup);

        setMembers(sut, intro, tripletGroup);
    }

    @Test
    @Override
    public void testGetParameters() {
        java.util.Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        testParameters(expectedParams, sut);
    }
}
