package org.modica.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletGroupTestCase;
import org.modica.afp.modca.structuredfields.types.MapType;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;

import static org.junit.Assert.assertEquals;

/**
 * A test case for {@link MapCodedFont}.
 */
public class MapCodedFontTestCase extends StructuredFieldWithTripletGroupTestCase<MapCodedFont> {

    private MapCodedFont sut;
    private Context ctx;

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(MapType.MCF);
        RepeatingTripletGroup tripletGroup = StructuredFieldWithTripletGroupTestCase.createGenericRepeatingGroup();

        ctx = new ContextImpl();
        sut = new MapCodedFont(intro, tripletGroup, ctx);

        setMembers(sut, intro, tripletGroup);
    }

    @Test
    public void testContext() {
        assertEquals(sut, ctx.get(ContextType.MODCA_MAP_CODED_FONT));
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        testParameters(expectedParams, sut);
    }
}
