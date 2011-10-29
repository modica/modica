package org.afpparser.common;

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
}
