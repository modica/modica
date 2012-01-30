package org.afpparser.afp.modca.structuredfields.descriptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.ioca.IocaFunctionSetId;
import org.afpparser.afp.ioca.SelfDefiningField;
import org.afpparser.afp.ioca.SetBilevelImageColor;
import org.afpparser.afp.ioca.SetExtendedBilevelImageColor;
import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.PresentationSpaceUnits;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Descriptor;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link ImageDataDescriptor}.
 */
public class ImageDataDescriptorTestCase extends StructuredFieldTestCase<ImageDataDescriptor> {

    private ImageDataDescriptor oneSelfDefiningField;
    private ImageDataDescriptor severalSelfDefiningFields;

    @Before
    public void setUp() {
        StructuredFieldIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Descriptor.IID);
        byte[] bytes = ByteUtils.createByteArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 0xF7, 2, 1, 0x0A);
        Parameters params = new Parameters(bytes, "Cp500");
        oneSelfDefiningField = new ImageDataDescriptor(intro, params);

        ByteBuffer bb = ByteBuffer.allocate(32);
        byte[] setBilevelImageColor = ByteUtils.createByteArray(0xF6, 4, 0, 0, 0, 1);
        byte[] setExtendedBilevelImageColor =
                ByteUtils.createByteArray(0xF4, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

        bb.put(bytes);
        bb.put(setExtendedBilevelImageColor);
        bb.put(setBilevelImageColor);
        Parameters newParams = new Parameters(bb.array(), "Cp500");
        severalSelfDefiningFields = new ImageDataDescriptor(intro, newParams);
        setMembers(severalSelfDefiningFields, intro);

        try {
            bytes[9] = (byte) 0xFF;
            params = new Parameters(bytes, "Cp500");
            new ImageDataDescriptor(intro, params);
            fail("Failed to throw exception");
        } catch (IllegalArgumentException iae) {
            // Pass
        }
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, oneSelfDefiningField.getXResol());
        assertEquals(0x304, oneSelfDefiningField.getYResol());
        assertEquals(0x506, oneSelfDefiningField.getXSize());
        assertEquals(0x708, oneSelfDefiningField.getYSize());
        assertEquals(PresentationSpaceUnits.INCHES_10, oneSelfDefiningField.getUnitsBase());
        List<SelfDefiningField> oneSdf = oneSelfDefiningField.getSelfDefiningFields();
        assertEquals(1, oneSdf.size());
        assertTrue(oneSdf.get(0) instanceof IocaFunctionSetId);

        List<SelfDefiningField> severalSdfs = severalSelfDefiningFields.getSelfDefiningFields();
        assertEquals(3, severalSdfs.size());
        assertTrue(severalSdfs.get(0) instanceof IocaFunctionSetId);
        assertTrue(severalSdfs.get(1) instanceof SetExtendedBilevelImageColor);
        assertTrue(severalSdfs.get(2) instanceof SetBilevelImageColor);
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("XResolution", 0x102));
        expectedParams.add(new ParameterAsString("YResolution", 0x304));
        expectedParams.add(new ParameterAsString("XSize", 0x506));
        expectedParams.add(new ParameterAsString("YSize", 0x708));
        expectedParams.add(new ParameterAsString("UnitBase", "INCHES_10"));
        testParameters(expectedParams, oneSelfDefiningField);
    }
}
