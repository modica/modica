package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

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
        FQNCharStringData fcsn = (FQNCharStringData) fqn;
        assertEquals(expectedData, fcsn.getString());
    }
    /*
     * @Test public void testOidDecodingExample3() { byte[] example1 =
     * ByteUtils.hexToBytes("0603813403"); ObjectId example1Oid = ObjectId.parse(example1, 0);
     * byte[] actual = example1Oid.getOid(); byte[] expected = ByteUtils.createByteArray(2, 100, 3);
     * assertArrayEquals(expected, actual); }
     *
     * @Test public void testOidDecodingExample1() { byte[] encodedOid =
     * ByteUtils.hexToBytes("06018148"); ObjectId oid = ObjectId.parse(encodedOid, 0); byte[]
     * expected = ByteUtils.createByteArray(0xC8); assertArrayEquals(expected, oid.getOid()); }
     *
     * @Test public void testOidDecodingExample2() { byte[] encodedOid =
     * ByteUtils.hexToBytes("060103"); ObjectId oid = ObjectId.parse(encodedOid, 0); byte[] expected
     * = ByteUtils.createByteArray(0x03); assertArrayEquals(expected, oid.getOid()); }
     */
}
