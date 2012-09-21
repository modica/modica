package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.modica.afp.modca.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test case for {@link EndFieldName}.
 */
public class EndFieldNameTestCase {

    @Test
    public void testGetters() throws UnsupportedEncodingException {
        byte[] bytes = "Only the first 8 characters should be taken".getBytes("Cp500");

        Parameters params = new Parameters(bytes);
        EndFieldName fieldName = new EndFieldName(params);
        assertEquals("Only the", fieldName.getName());
        assertFalse(fieldName.matchesAny());
        assertEquals("Only the", fieldName.toString());

        bytes[0] = (byte) 0xff;
        bytes[1] = (byte) 0xff;

        params = new Parameters(bytes);
        fieldName = new EndFieldName(params);
        assertNull(fieldName.getName());
        assertTrue(fieldName.matchesAny());
        assertEquals("matches any", fieldName.toString());
    }
}
