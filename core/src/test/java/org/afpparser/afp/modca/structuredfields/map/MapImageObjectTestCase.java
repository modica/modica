package org.afpparser.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;

import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Map;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletGroupTestCase;
import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link MapImageObject}.
 */
public class MapImageObjectTestCase extends StructuredFieldWithTripletGroupTestCase<MapImageObject> {

    private MapImageObject sut;

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Map.MIO);
        RepeatingTripletGroup repeatingGroup = createGenericRepeatingGroup();

        sut = new MapImageObject(intro, repeatingGroup);
        setMembers(sut, intro, repeatingGroup);
    }

    @Test
    @Override
    public void testGetParameters() {
        java.util.Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        testParameters(expectedParams, sut);
    }
}
