package org.afpparser.afp.modca;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class ParameterAsStringTestCase {

    @Test
    public void testKeyValue() {
        ParameterAsString sut = new ParameterAsString("key", "Value");
        assertEquals("key", sut.getKey());
        assertEquals("Value", sut.getValue());
    }

    public static void testParameters(List<ParameterAsString> expected,
            List<ParameterAsString> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getKey(), actual.get(i).getKey());
            assertEquals(expected.get(i).getValue(), actual.get(i).getValue());
        }
    }
}
