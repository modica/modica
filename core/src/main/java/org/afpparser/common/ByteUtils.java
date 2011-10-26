package org.afpparser.common;

/**
 * A utility class to assist with binary operations. 
 */
public abstract class ByteUtils {

    private static final ByteUtils littleEndianUtils = new LittleEndianByteUtils();
    private static final ByteUtils bigEndianUtils = new BigEndianByteUtils();

    private ByteUtils() {
    }

    /**
     * Takes an array of bytes within <code>bytes</code> starting at <code>position</code> with
     * <code>length</code> number of bytes and returns the <code>int</code> representation. An
     * integer primitive is 32bits so this can take up to 4 bytes as the length.
     *
     * @param bytes the byte array to extract the integer from
     * @param position the byte offset within <code>bytes</code>
     * @param length the number of bytes to create the integer from
     * @return an integer
     */
    public abstract int bytesToInt(byte[] bytes, int position, int length);
    
    /**
     * Takes an array of bytes and starting at the zeroth offset for <code>bytes.length</code> bytes
     * returns the <code>int</code> representation. An integer primitive is 32bits so this can take
     * up to 4 bytes as the length.
     *
     * @param bytes the byte array to extract the integer from
     * @return an integer
     */
    public abstract int bytesToInt(byte[] bytes);

    private static final int bytesToInt(byte[] bytes, int position, int length,
            int bitShiftStart, int bitShitIncrement) {
        int num = 0;
        if (length > 4) {
            throw new IllegalArgumentException("The maximum capacity for an int is 32 bytes");
        }
        for (int i = 0, bitShift = bitShiftStart; i < length; i++, bitShift += bitShitIncrement) {
            num += (bytes[position + i] & 0xff) << bitShift;
        }
        return num;
    }

    /**
     * Converts a hexadecimal string to its byte array equivalent, starting at <code>position</code>
     * taking <code>length</code> characters.
     *
     * @param hex the hexadecimal string
     * @param position the character offset within <code>hex</code>
     * @param length the number of characters to convert to bytes
     * @return the byte array representation
     */
    public static final byte[] hexToBytes(String hex, int position, int length) {
        if (length % 2 > 0) {
            throw new IllegalArgumentException("A valid hexadecimal string would have an even"
                    + "number of characters.");
        }
        int numOfBytes = length / 2;
        byte[] data = new byte[numOfBytes];
        char[] hexArray = hex.toCharArray();
        for (int i = 0, charIndex = position; i < numOfBytes; i++, charIndex += 2) {
            data[i] = (byte) ((Character.digit(hexArray[charIndex], 16) << 4)
                    + (Character.digit(hexArray[charIndex + 1], 16)));
        }
        return data;
    }

    /**
     * Converts a hexadecimal string to its byte array equivalent, starting at the zeroth offset
     * to <code>hex.length()</code>.
     *
     * @param hex the hexadecimal string
     * @return the byte array representation
     */
    public static final byte[] hexToBytes(String hex) {
        return hexToBytes(hex, 0, hex.length());
    }

    /**
     * Converts an array of bytes starting at <code>position</code> for <code>length</code> bytes
     * to a hexadecimal string.
     *
     * @param bytes the byte array convert into hex
     * @param position the byte offset to being converting
     * @param length the number of bytes to convert
     * @return a hexadecimal string
     */
    public static final String byteToHex(byte[] bytes, int position, int length) {
        StringBuffer buffer = new StringBuffer(length * 2);
        for (int i = position; i < position + length; i++) {
            buffer.append(Integer.toBinaryString(bytes[i] & 0xff));
        }
        return buffer.toString();
    }

    /**
     * Creates a byte array from a series of integers. This is purely to save all the headaches
     * with writing (byte) 0x00 for every element within an array.
     *
     * @param element
     * @return
     */
    public static byte[] createByteArray(int... element) {
        byte[] byteArray = new byte[element.length];
        for (int i = 0; i < element.length; i++) {
            byteArray[i] = (byte) element[i];
        }
        return byteArray;
    }

    private static class LittleEndianByteUtils extends ByteUtils {

        private LittleEndianByteUtils() {
        }

        @Override
        public int bytesToInt(byte[] bytes, int position, int length) {
            return bytesToInt(bytes, position, length, (length - 1) * 8, -8);
        }

        @Override
        public int bytesToInt(byte[] bytes) {
            return bytesToInt(bytes, 0, bytes.length);
        }

    }

    private static class BigEndianByteUtils extends ByteUtils {

        private BigEndianByteUtils() {
        }

        @Override
        public int bytesToInt(byte[] bytes, int position, int length) {
            return bytesToInt(bytes, position, length, 0, 8);
        }

        @Override
        public int bytesToInt(byte[] bytes) {
            return bytesToInt(bytes, 0, bytes.length);
        }

    }

    /**
     * Returns a a little endian binary utility.
     *
     * @return a little endian binary assistant
     */
    public static ByteUtils newLittleEndianUtils() {
        return littleEndianUtils;
    }

    /**
     * Returns a big endian binary utility.
     *
     * @return a big endian binary assitant
     */
    public static ByteUtils newBigEndianUtils() {
        return bigEndianUtils;
    }
}
