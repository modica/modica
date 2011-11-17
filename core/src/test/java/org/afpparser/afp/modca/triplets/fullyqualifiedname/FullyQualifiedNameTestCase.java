package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.afpparser.common.ByteUtils;
import org.junit.Test;

/**
 * Test case for {@link FullyQualifiedName}.
 */
public class FullyQualifiedNameTestCase {

    @Test
    public void testFontCharSetNameRef() throws UnsupportedEncodingException, MalformedURLException {
        String fqnString = "028600C3F0C8F2F0F0D1F0";
        int length = 0x0C;
        testFQNWithCharData("C0H200J0", length, FQNType.font_charset_name_ref, fqnString);
    }

    @Test
    public void testCodePageNameRef() throws UnsupportedEncodingException, MalformedURLException {
        String fqnString = "028500E3F1E5F1F0F5F0F0";
        int length = 0x0C;
        testFQNWithCharData("T1V10500", length, FQNType.code_page_name_ref, fqnString);
    }

    private void testFQNWithCharData(String expectedStr, int length, FQNType type, String hexData)
            throws UnsupportedEncodingException, MalformedURLException {
        byte[] bytes = ByteUtils.hexToBytes(hexData);
        FullyQualifiedName fqn = FullyQualifiedName.parse(bytes, 0, length);
        assertEquals(length, fqn.getLength());
        assertEquals(type, fqn.getFQNType());
        FQNCharStringData fcsn = (FQNCharStringData) fqn;
        assertEquals(expectedStr, fcsn.getString());
    }
}
