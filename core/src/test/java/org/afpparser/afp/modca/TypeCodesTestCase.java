package org.afpparser.afp.modca;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A test case for {@link TypeCodes}.
 */
public class TypeCodesTestCase {

    @Test
    public void testTypeCodes() {
        testTypeCodeValue(0xA0, TypeCodes.Attribute);
        testTypeCodeValue(0xA2, TypeCodes.CopyCount);
        testTypeCodeValue(0xA6, TypeCodes.Descriptor );
        testTypeCodeValue(0xA7, TypeCodes.Control);
        testTypeCodeValue(0xA8, TypeCodes.Begin);
        testTypeCodeValue(0xA9, TypeCodes.End);
        testTypeCodeValue(0xAB, TypeCodes.Map);
        testTypeCodeValue(0xAC, TypeCodes.Position);
        testTypeCodeValue(0xAD, TypeCodes.Process);
        testTypeCodeValue(0xAF, TypeCodes.Include);
        testTypeCodeValue(0xB0, TypeCodes.Table);
        testTypeCodeValue(0xB1, TypeCodes.Migration);
        testTypeCodeValue(0xB2, TypeCodes.Variable);
        testTypeCodeValue(0xB4, TypeCodes.Link);
        testTypeCodeValue(0xEE, TypeCodes.Data);
    }

    private void testTypeCodeValue(int expected, TypeCodes typeCode) {
        assertEquals((byte) expected, typeCode.getValue());
    }
}
