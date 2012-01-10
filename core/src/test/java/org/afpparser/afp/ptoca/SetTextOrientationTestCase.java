package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

public class SetTextOrientationTestCase extends ControlSequenceTestCase<SetTextOrientation> {

    private SetTextOrientation sut;
    private SetTextOrientation oneDegree;
    private SetTextOrientation tenDegrees;
    private SetTextOrientation hundredDegrees;

    @Before
    public void setUp() {
        Parameters params = new Parameters(ByteUtils.createByteArray(0xB3, 0x81, 0xB3, 0x81), "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.SET_TEXT_ORIENTATION;
        int length = 4;
        boolean isChained = true;

        sut = new SetTextOrientation(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);

        params = new Parameters(ByteUtils.createByteArray(0, 0x82, 0, 0x83), "Cp500");
        oneDegree = new SetTextOrientation(expectedCsId, length, isChained, params);

        params = new Parameters(ByteUtils.createByteArray(5, 0x04, 5, 0x05), "Cp500");
        tenDegrees = new SetTextOrientation(expectedCsId, length, isChained, params);

        params = new Parameters(ByteUtils.createByteArray(50, 0x06, 50, 0x07), "Cp500");
        hundredDegrees = new SetTextOrientation(expectedCsId, length, isChained, params);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(359, sut.getIOrientationDegrees());
        assertEquals(1, sut.getIOrientationMinutes());
        assertEquals(359, sut.getBOrientationDegrees());
        assertEquals(1, sut.getBOrientationMinutes());
        assertEquals(valueAsString(359, 1, 359, 1), sut.getValueAsString());

        assertEquals(1, oneDegree.getIOrientationDegrees());
        assertEquals(2, oneDegree.getIOrientationMinutes());
        assertEquals(1, oneDegree.getBOrientationDegrees());
        assertEquals(3, oneDegree.getBOrientationMinutes());
        assertEquals(valueAsString(1, 2, 1, 3), oneDegree.getValueAsString());

        assertEquals(10, tenDegrees.getIOrientationDegrees());
        assertEquals(4, tenDegrees.getIOrientationMinutes());
        assertEquals(10, tenDegrees.getBOrientationDegrees());
        assertEquals(5, tenDegrees.getBOrientationMinutes());
        assertEquals(valueAsString(10, 4, 10, 5), tenDegrees.getValueAsString());

        assertEquals(100, hundredDegrees.getIOrientationDegrees());
        assertEquals(6, hundredDegrees.getIOrientationMinutes());
        assertEquals(100, hundredDegrees.getBOrientationDegrees());
        assertEquals(7, hundredDegrees.getBOrientationMinutes());
        assertEquals(valueAsString(100, 6, 100, 7), hundredDegrees.getValueAsString());
    }

    private String valueAsString(int iDegs, int iMins, int bDegs, int bMins) {
        return "I-Axis=" + iDegs + "\"" + iMins + "' "
                + "B-Axis=" + bDegs + "\"" + bMins + "'";
    }
}
