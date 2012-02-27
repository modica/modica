package org.afpparser.afp.ioca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.common.ByteUtils;
import org.junit.Test;

/**
 * Test case for {@link IocaFunctionSetId}
 */
public class IocaFunctionSetIdTestCase {

    @Test
    public void testConstructor() {
        Parameters params = new Parameters(ByteUtils.createByteArray(0x02, 0x01, 0x0A), "Cp500");
        IocaFunctionSetId sut = new IocaFunctionSetId(params);
        assertEquals(FunctionSet.FS_10, sut.getFunctionSet());
        assertEquals(3, sut.getLength());
        assertEquals((byte) 0xF7, sut.getId());

        // test other Function set values
        testFunctionSetGetter(FunctionSet.FS_11, 0x0B);
        testFunctionSetGetter(FunctionSet.FS_40, 0x28);
        testFunctionSetGetter(FunctionSet.FS_42, 0x2A);
        testFunctionSetGetter(FunctionSet.FS_45, 0x2D);

        try {
            testFunctionSetGetter(FunctionSet.FS_10, 0x11);
            fail("Should throw an exception if an illegal FunctionSet id is given.");
        } catch (IllegalArgumentException iae) {
            // Pass
        }
    }

    private void testFunctionSetGetter(FunctionSet expected, int id) {
        Parameters params = new Parameters(ByteUtils.createByteArray(0x02, 0x01, id), "Cp500");
        IocaFunctionSetId sut = new IocaFunctionSetId(params);
        assertEquals(expected, sut.getFunctionSet());

        assertEquals(1, sut.getParameters().size());
        assertEquals("FunctionSet", sut.getParameters().get(0).getKey());
        assertEquals(expected.toString(), sut.getParameters().get(0).getValue());
        assertEquals("IOCAFunctionsetId", sut.getName());
    }
}
