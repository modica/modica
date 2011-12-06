package org.afpparser.afp.modca.structuredfields.end;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Parameters;
import org.junit.Test;

/**
 * Test case for {@link EndFieldName}.
 */
public class EndFieldNameTestCase {

    @Test
    public void testGetters() throws UnsupportedEncodingException {
        byte[] bytes = "Only the first 8 characters should be taken".getBytes("Cp500");

        Parameters params = new Parameters(bytes, "Cp500");
        EndFieldName fieldName = new EndFieldName(params);
        assertEquals("Only the", fieldName.getName());
        assertFalse(fieldName.matchesAny());

        bytes[0] = (byte) 0xff;
        bytes[1] = (byte) 0xff;

        params = new Parameters(bytes, "Cp500");
        fieldName = new EndFieldName(params);
        assertNull(fieldName.getName());
        assertTrue(fieldName.matchesAny());
    }
}
