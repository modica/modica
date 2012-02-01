package org.afpparser.afp.modca.structuredfields.descriptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.EncodingScheme;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Descriptor;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CodePageDescriptor}.
 */
public class CodePageDescriptorTestCase extends StructuredFieldTestCase<CodePageDescriptor> {

    private CodePageDescriptor sut;
    private CodePageDescriptor sutNoScheme;
    private final String cpDesc = "This string has to be 32 charss.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(Descriptor.CPD);

        ByteBuffer bb = ByteBuffer.allocate(44);
        bb.put(cpDesc.getBytes("Cp500"));
        byte[] paramsBytes = ByteUtils.createByteArray(0, 8, 1, 2, 3, 4, 5, 6, 7, 8, 0x62, 0);
        bb.put(paramsBytes);
        Parameters params = new Parameters(bb.array(), "Cp500");
        sut = new CodePageDescriptor(intro, params);

        setMembers(sut, intro);

        byte[] noSchemeBytes = ByteUtils.createByteArray(0, 8, 1, 2, 3, 4, 5, 6, 7, 8);
        bb = ByteBuffer.allocate(42);
        bb.put(cpDesc.getBytes("Cp500"));
        bb.put(noSchemeBytes);
        sutNoScheme = new CodePageDescriptor(intro, new Parameters(bb.array(), "Cp500"));
    }

    @Test
    public void testGetterMethods() {
        assertEquals(cpDesc, sut.getCodePageDescriptor());
        assertEquals(0x1020304, sut.getNumCdPts());
        assertEquals(0x506, sut.getGcsgid());
        assertEquals(0x708, sut.getCpgid());
        assertEquals(EncodingScheme.DOUBLE_BYTE_EBCDIC, sut.getEncodingScheme());
        assertNull(sutNoScheme.getEncodingScheme());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("Description", cpDesc));
        expectedParams.add(new ParameterAsString("NumberOfCodePoints", 16909060));
        expectedParams.add(new ParameterAsString("GCSGID", 1286));
        expectedParams.add(new ParameterAsString("CPGID", 1800));
        expectedParams.add(new ParameterAsString("EncodingScheme",
                EncodingScheme.DOUBLE_BYTE_EBCDIC));
        testParameters(expectedParams, sut);
    }
}
