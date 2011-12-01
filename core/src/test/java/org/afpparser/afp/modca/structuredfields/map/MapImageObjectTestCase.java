package org.afpparser.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Map;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletGroupTestCase;
import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
import org.junit.Before;

/**
 * Test case for {@link MapImageObject}.
 */
public class MapImageObjectTestCase extends StructuredFieldWithTripletGroupTestCase<MapImageObject> {

    private MapImageObject sut;

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Map.MIO);
        RepeatingTripletGroup repeatingGroup = createGenericRepeatingGroup();

        sut = new MapImageObject(intro, repeatingGroup);
        setMembers(sut, intro, repeatingGroup);
    }
}
