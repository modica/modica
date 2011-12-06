package org.afpparser.afp.modca.structuredfields.include;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.List;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.ReferenceCoordinateSystem;
import org.afpparser.afp.modca.common.Rotation;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Include;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.afpparser.afp.modca.structuredfields.include.IncludeObject.ObjectType;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;


/**
 * Test case for {@link IncludeObject}.
 */
public class IncludeObjectTestCase extends StructuredFieldWithTripletsTestCase<IncludeObject> {

    private IncludeObject hasAllParams;
    private IncludeObject useXOriginArea;
    private IncludeObject useYOriginArea;
    private IncludeObject useXObjectRotation;
    private IncludeObject useYObjectRotation;
    private IncludeObject useXOriginOffset;
    private IncludeObject useYOriginOffset;

    private final String objName = "objsName";

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Include.IOB);

        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);

        ByteBuffer bb = ByteBuffer.allocate(27);
        bb.put(objName.getBytes("Cp500"));
        byte[] paramByteArray = ByteUtils.createByteArray(0, 0xBB,
                1, 2, 3,
                4, 5, 6,
                0, 0,
                0x2D, 0,
                7, 8, 9,
                10, 11, 12,
                1);

        bb.put(paramByteArray);
        byte[] constructorArray = bb.array();
        Parameters params = new Parameters(constructorArray, "Cp500");
        hasAllParams = new IncludeObject(intro, triplets, params);
        setMembers(hasAllParams, intro, triplets);

        fillWithFF(constructorArray, 10, 3);
        Parameters useXOA = new Parameters(constructorArray, "Cp500");
        useXOriginArea = new IncludeObject(intro, triplets, useXOA);

        fillWithFF(constructorArray, 13, 3);
        params = new Parameters(constructorArray, "Cp500");
        useYOriginArea = new IncludeObject(intro, triplets, params);

        fillWithFF(constructorArray, 16, 2);
        params = new Parameters(constructorArray, "Cp500");
        useXObjectRotation = new IncludeObject(intro, triplets, params);

        fillWithFF(constructorArray, 18, 2);
        params = new Parameters(constructorArray, "Cp500");
        useYObjectRotation = new IncludeObject(intro, triplets, params);

        fillWithFF(constructorArray, 20, 3);
        params = new Parameters(constructorArray, "Cp500");
        useXOriginOffset = new IncludeObject(intro, triplets, params);

        fillWithFF(constructorArray, 23, 3);
        params = new Parameters(constructorArray, "Cp500");
        useYOriginOffset = new IncludeObject(intro, triplets, params);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(ObjectType.GRAPHICS_GOCA, hasAllParams.getObjType());
        assertEquals(objName, hasAllParams.getObjName());
        assertEquals(0x10203, hasAllParams.getXoaOset());
        assertEquals(false, hasAllParams.useXAxisOriginArea());
        assertEquals(0x40506, hasAllParams.getYoaOset());
        assertEquals(false, hasAllParams.useYAxisOriginArea());
        assertEquals(Rotation.ZERO, hasAllParams.getXoaOrent());
        assertEquals(false, hasAllParams.useXAxisObjectRotation());
        assertEquals(Rotation.NINETY, hasAllParams.getYoaOrent());
        assertEquals(false, hasAllParams.useYAxisObjectRotation());
        assertEquals(0x70809, hasAllParams.getXocaOset());
        assertEquals(false, hasAllParams.useXAxisOriginOffset());
        assertEquals(0xA0B0C, hasAllParams.getYocaOset());
        assertEquals(false, hasAllParams.useYAxisOriginOffset());
        assertEquals(ReferenceCoordinateSystem.ORIGIN, hasAllParams.getRefCSys());

        assertEquals(0xFFFFFF, useXOriginArea.getXoaOset());
        assertEquals(true, useXOriginArea.useXAxisOriginArea());

        assertEquals(0xFFFFFF, useYOriginArea.getYoaOset());
        assertEquals(true, useYOriginArea.useYAxisOriginArea());

        assertEquals(null, useXObjectRotation.getXoaOrent());
        assertEquals(true, useXObjectRotation.useXAxisObjectRotation());

        assertEquals(null, useYObjectRotation.getYoaOrent());
        assertEquals(true, useYObjectRotation.useXAxisObjectRotation());

        assertEquals(0xFFFFFF, useXOriginOffset.getXocaOset());
        assertEquals(true, useXOriginOffset.useXAxisOriginOffset());

        assertEquals(0xFFFFFF, useYOriginOffset.getYocaOset());
        assertEquals(true, useYOriginOffset.useYAxisOriginOffset());
    }

    private void fillWithFF(byte[] array, int index, int length) {
        for (int i = index; i < index + length; i++) {
            array[i] = (byte) 0xFF;
        }
    }

}
