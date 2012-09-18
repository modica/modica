package org.modica.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletGroupTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Map;
import org.modica.afp.modca.structuredfields.map.MapImageObject;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;

/**
 * Test case for {@link MapImageObject}.
 */
public class MapImageObjectTestCase extends StructuredFieldWithTripletGroupTestCase<MapImageObject> {

    private MapImageObject sut;

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Map.MIO);
        RepeatingTripletGroup repeatingGroup = createGenericRepeatingGroup();

        sut = new MapImageObject(intro, repeatingGroup);
        setMembers(sut, intro, repeatingGroup);
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        testParameters(expectedParams, sut);
    }
}
