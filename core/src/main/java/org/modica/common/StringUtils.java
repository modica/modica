package org.modica.common;

import java.io.UnsupportedEncodingException;

/**
 * A utility class for string manipulation.
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * Converts the given long to a hexadecimal string of length characters.
     *
     * @param l the long to convert to hex
     * @param length the number of characters for the returned string
     * @return the hexadecimal string
     */
    public static String toHex(long l, int length) {
        return String.format("%" + length + "s", Long.toHexString(l)).replace(' ', '0');
    }

    /**
     * Converts the given integer to a hexadecimal string of length characters.
     *
     * @param i the integer to convert to hex
     * @param length the number of characters for the returned string
     * @return the hexadecimal string
     */
    public static String toHex(int i, int length) {
        return String.format("%" + length + "s", Integer.toHexString(i)).replace(' ', '0');
    }

    public static String bytesToCp500(byte[] bytes, int offset, int length)
            throws UnsupportedEncodingException {
        return new String(bytes, offset, length, "Cp500");
    }

    public static String bytesToCp500(byte[] bytes) throws UnsupportedEncodingException {
        return bytesToCp500(bytes, 0, bytes.length);
    }

    public static String bytesToString(byte[] bytes, int offset, int length, String encoding)
            throws UnsupportedEncodingException {
        return new String(bytes, offset, length, encoding);
    }

    public static String bytesToCp500(byte[] bytes, String encoding)
            throws UnsupportedEncodingException {
        return bytesToString(bytes, 0, bytes.length, encoding);
    }
}
