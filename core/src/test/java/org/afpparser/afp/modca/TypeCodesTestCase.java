package org.afpparser.afp.modca;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.structuredfields.TypeCode;
import org.junit.Test;

/**
 * A test case for {@link TypeCode}.
 */
public class TypeCodesTestCase {

    @Test
    public void testTypeCodes() {
        testTypeCodeValue(0xA0, TypeCode.Attribute);
        testTypeCodeValue(0xA2, TypeCode.CopyCount);
        testTypeCodeValue(0xA6, TypeCode.Descriptor );
        testTypeCodeValue(0xA7, TypeCode.Control);
        testTypeCodeValue(0xA8, TypeCode.Begin);
        testTypeCodeValue(0xA9, TypeCode.End);
        testTypeCodeValue(0xAB, TypeCode.Map);
        testTypeCodeValue(0xAC, TypeCode.Position);
        testTypeCodeValue(0xAD, TypeCode.Process);
        testTypeCodeValue(0xAF, TypeCode.Include);
        testTypeCodeValue(0xB0, TypeCode.Table);
        testTypeCodeValue(0xB1, TypeCode.Migration);
        testTypeCodeValue(0xB2, TypeCode.Variable);
        testTypeCodeValue(0xB4, TypeCode.Link);
        testTypeCodeValue(0xEE, TypeCode.Data);
    }

    private void testTypeCodeValue(int expected, TypeCode typeCode) {
        assertEquals((byte) expected, typeCode.getValue());
    }
}
