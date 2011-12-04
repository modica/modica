package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.common.ByteUtils;
import org.junit.Test;

/**
 * Test case for {@link FullyQualifiedName}.
 */
public class FullyQualifiedNameTestCase {

    public static final String FONT_CHAR_SET_NAME_REF = "0C028600C3F0C8F2F0F0D1F0";
    public static final String CODE_PAGE_NAME_REF = "0C028500E3F1E5F1F0F5F0F0";

    @Test
    public void testFontCharSetNameRef() throws UnsupportedEncodingException, MalformedURLException {
        int length = 0x0C;
        testFQNWithCharData("C0H200J0", length, FQNType.font_charset_name_ref,
                FONT_CHAR_SET_NAME_REF);
    }

    @Test
    public void testCodePageNameRef() throws UnsupportedEncodingException, MalformedURLException {
        int length = 0x0C;
        testFQNWithCharData("T1V10500", length, FQNType.code_page_name_ref, CODE_PAGE_NAME_REF);
    }

    private void testFQNWithCharData(String expectedStr, int length, FQNType type, String hexData)
            throws UnsupportedEncodingException, MalformedURLException {
        FullyQualifiedName fqn = createFQN(hexData);
        assertEquals(length, fqn.getLength());
        assertEquals(type, fqn.getFQNType());
        FQNCharStringData fcsn = (FQNCharStringData) fqn;
        assertEquals(expectedStr, fcsn.getString());
    }

    public static FullyQualifiedName createFQN(String hexData) throws MalformedURLException,
            UnsupportedEncodingException {
        byte[] bytes = ByteUtils.hexToBytes(hexData);
        Parameters params = new Parameters(bytes);
        params.skip(2);
        return FullyQualifiedName.parse(params, (hexData.length() / 2));
    }
}
