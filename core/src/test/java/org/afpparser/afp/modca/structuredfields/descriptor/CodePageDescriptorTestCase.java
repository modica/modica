package org.afpparser.afp.modca.structuredfields.descriptor;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.EncodingScheme;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Descriptor;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CodePageDescriptor}.
 */
public class CodePageDescriptorTestCase extends StructuredFieldTestCase<CodePageDescriptor> {

    private CodePageDescriptor sut;
    private final String cpDesc = "This string has to be 32 charss.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Descriptor.CPD);

        ByteBuffer bb = ByteBuffer.allocate(44);
        bb.put(cpDesc.getBytes("Cp500"));
        byte[] paramsBytes = ByteUtils.createByteArray(0, 8, 1, 2, 3, 4, 5, 6, 7, 8, 0x62, 0);
        bb.put(paramsBytes);
        Parameters params = new Parameters(bb.array(), "Cp500");
        sut = new CodePageDescriptor(intro, params);

        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x1020304, sut.getNumCdPts());
        assertEquals(0x506, sut.getGcsgid());
        assertEquals(0x708, sut.getCpgid());
        assertEquals(EncodingScheme.DOUBLE_BYTE_EBCDIC, sut.getEncodingScheme());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("Description", cpDesc);
        expectedParams.put("NumberOfCodePoints", "16909060");
        expectedParams.put("GCSGID", String.valueOf("1286"));
        expectedParams.put("CPGID", String.valueOf("1800"));
        expectedParams.put("EncodingScheme", EncodingScheme.DOUBLE_BYTE_EBCDIC.toString());
        testParameters(expectedParams, sut);
    }
}
