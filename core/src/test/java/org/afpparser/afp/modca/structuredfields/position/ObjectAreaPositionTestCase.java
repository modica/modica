package org.afpparser.afp.modca.structuredfields.position;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.ReferenceCoordinateSystem;
import org.afpparser.afp.modca.common.Rotation;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.SfIntroducerTestCase;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Position;
import org.afpparser.afp.modca.structuredfields.StructuredFieldTestCase;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link ObjectAreaPosition}.
 */
public class ObjectAreaPositionTestCase extends StructuredFieldTestCase<ObjectAreaPosition> {

    private ObjectAreaPosition sut;

    @Before
    public void setUp() {
        SfIntroducer intro = SfIntroducerTestCase.createGenericIntroducer(Position.OBP);
        byte[] data = ByteUtils.createByteArray(
                1, // oaPosId
                23, //Length must be 23
                2, 3, 4, // xoaOset
                5, 6, 7, // yoaOset
                0, 0, // xoaOrent
                0x2D, 0, // yoaOrent
                0,
                1, 2, 3, //xocaOset
                4, 5, 6, // yocaOset
                0, 0, // xocaOrent must be 0
                0x2D, 0, //yocaOrent
                1);// refCSys
        Parameters params = new Parameters(data, "Cp500");
        sut = new ObjectAreaPosition(intro, params);

        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(1, sut.getOaPosId());
        assertEquals(0x20304, sut.getXoaOset());
        assertEquals(0x50607, sut.getYoaOset());
        assertEquals(Rotation.ZERO, sut.getXoaOrent());
        assertEquals(Rotation.NINETY, sut.getYoaOrent());
        assertEquals(0x10203, sut.getXocaOset());
        assertEquals(0x40506, sut.getYocaOset());
        assertEquals(Rotation.ZERO, sut.getXocaOrent());
        assertEquals(Rotation.NINETY, sut.getYocaOrent());
        assertEquals(ReferenceCoordinateSystem.ORIGIN, sut.getRefCSys());
    }

    @Test
    @Override
    public void testGetParameters() {
        Map<String, String> expectedParams = new LinkedHashMap<String, String>();
        expectedParams.put("ObjectAreaPositionId", ByteUtils.bytesToHex((byte) 0x01));
        expectedParams.put("X-AxisObjectAreaOffset", String.valueOf(0x20304));
        expectedParams.put("Y-AxisObjectAreaOffset", String.valueOf(0x50607));
        expectedParams.put("X-AxisObjectOrientation", Rotation.ZERO.toString());
        expectedParams.put("Y-AxisObjectOrientation", Rotation.NINETY.toString());
        expectedParams.put("X-AxisObjectOffset", String.valueOf(0x10203));
        expectedParams.put("Y-AxisObjectOffset", String.valueOf(0x40506));
        expectedParams.put("ReferenceCoordSystem", ReferenceCoordinateSystem.ORIGIN.toString());
        testParameters(expectedParams, sut);
    }
}
