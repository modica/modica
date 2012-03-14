package org.modica.afp.modca.triplets;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.MODCAContext;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.Cgcsgid.Ccsid;
import org.modica.afp.modca.triplets.Cgcsgid.Cpgid;
import org.modica.common.ByteUtils;
import org.modica.common.StringUtils;

/**
 * Test case for {@link Cgcsgid}.
 */
public class CgcsgidTestCase extends TripletTestCase<Cgcsgid> {
    private Cpgid cgcsgid;
    private Ccsid ccsid;
    private final TripletIdentifiers tid = TripletIdentifiers.coded_graphic_character_set_global_identifier;
    private final Parameters cpgidBytes = new Parameters(
            ByteUtils.createByteArray(0x00, 0x01, 0x01, 0x02), "Cp500");
    private final Parameters ccsidBytes = new Parameters(
            ByteUtils.createByteArray(0x00, 0x00, 0x01, 0x02), "Cp500");
    private Context context;

    @Before
    @Override
    public void setUp() {
        // Tests CPGID
        context = new ContextImpl();
        cgcsgid = (Cpgid) getValue(cpgidBytes);
        ccsid = (Ccsid) getValue(ccsidBytes);

        Cgcsgid y = getValue(cpgidBytes);
        Cgcsgid z = getValue(cpgidBytes);
        Parameters notEqualBytes = new Parameters(
                ByteUtils.createByteArray(0x00, 0x00, 0x01, 0x03), "Cp500");
        Cgcsgid notEqual = Cgcsgid.parse(notEqualBytes, context);
        setXYZ(cgcsgid, y, z, notEqual);
    }

    @Test
    public void testCcsidEqualsHashCode() {
        ccsid = (Ccsid) getValue(ccsidBytes);
        Cgcsgid y = getValue(ccsidBytes);
        Cgcsgid z = getValue(ccsidBytes);
        Parameters notEqualBytes = new Parameters(
                ByteUtils.createByteArray(0x00, 0x01, 0x01, 0x03), "Cp500");
        Cgcsgid notEqual = getValue(notEqualBytes);
        setXYZ(ccsid, y, z, notEqual);
        testEqualsHashCode();
    }

    @Test
    public void testGetters() {
        // cpgid getters
        assertEquals(0x06, cgcsgid.getLength());
        assertEquals(0x0001, cgcsgid.getGcsgid());
        assertEquals(0x0102, cgcsgid.getCpgid());
        assertEquals(tid, cgcsgid.getTid());

        assertEquals(0x06, ccsid.getLength());
        assertEquals(0x0102, ccsid.getCcsid());
        assertEquals(tid, ccsid.getTid());
        assertEquals("Cp258", context.get(MODCAContext.GCSGID));

        Parameters params = new Parameters(ByteUtils.createByteArray(0x00, 0x01, 0x00, 0x7B), "Cp123");
        Context ctx = new ContextImpl();
        // Cp500 is used as the default
        assertEquals("Cp500", ctx.get(MODCAContext.GCSGID));
        // Parsing the CGCSGID object changes the state of the context object to the appropriate
        // encoding code page
        Cgcsgid.parse(params, ctx);
        assertEquals("Cp123", ctx.get(MODCAContext.GCSGID));

    }

    private Cgcsgid getValue(Parameters params) {
        Cgcsgid value = Cgcsgid.parse(params, context);
        params.skipTo(0);
        return value;
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("GCSGID", StringUtils.toHex(0x0001, 4)));
        expectedParams.add(new ParameterAsString("CPGID", StringUtils.toHex(0x102, 4)));
        parameterTester(expectedParams, cgcsgid);

        expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("CCSID", StringUtils.toHex(0x102, 4)));
        parameterTester(expectedParams, ccsid);
    }
}
