package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.afpparser.common.ByteUtils;
import org.junit.Test;

/**
 * Test case for {@link FullyQualifiedName}.
 */
public class FullyQualifiedNameTestCase {

    @Test
    public void testFQNParsing() throws UnsupportedEncodingException {
        int length = 0x0C;
        String expectedData = "C0H20080";
        byte[] fontCharSetNameRef = ByteUtils.hexToBytes(
                "028600" + ByteUtils.bytesToHex(expectedData.getBytes("Cp500")));
        FullyQualifiedName fqn = FullyQualifiedName.parse(length, fontCharSetNameRef);
        assertEquals(fqn.getLength(), length);
        assertEquals(fqn.getFQNType(), FQNType.font_charset_name_ref);
        FontCharSetNameRef fcsn = (FontCharSetNameRef) fqn;
        assertEquals(fcsn.getString(), expectedData);
        FontCharSetNameRef f1 = new FontCharSetNameRef(0x0C, expectedData);
        assertTrue(f1.equals(fcsn));
    }
}
