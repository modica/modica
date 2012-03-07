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
import org.modica.afp.modca.structuredfields.SfTypeFactory.Map;
import org.modica.afp.modca.structuredfields.map.MapCodedFont;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;

/**
 * A test case for {@link MapCodedFont}.
 */
public class MapCodedFontTestCase extends StructuredFieldWithTripletGroupTestCase<MapCodedFont> {

    private MapCodedFont sut;

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Map.MCF);
        RepeatingTripletGroup tripletGroup = StructuredFieldWithTripletGroupTestCase.createGenericRepeatingGroup();

        sut = new MapCodedFont(intro, tripletGroup);

        setMembers(sut, intro, tripletGroup);
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        testParameters(expectedParams, sut);
    }
}
