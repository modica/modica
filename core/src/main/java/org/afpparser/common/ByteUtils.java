package org.afpparser.common;

public abstract class ByteUtils {

    private static final ByteUtils littleEndianUtils = new LittleEndianByteUtils();
    private static final ByteUtils bigEndianUtils = new BigEndianByteUtils();

    private ByteUtils() {
    }

    public abstract int bytesToInt(byte[] bytes, int position, int length);
    
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

    public static final byte[] hexToBytes(String hex) {
        return hexToBytes(hex, 0, hex.length());
    }

    public static final String byteToHex(byte[] bytes, int position, int length) {
        StringBuffer buffer = new StringBuffer(length * 2);
        for (int i = position; i < position + length; i++) {
            buffer.append(Integer.toBinaryString(bytes[i] & 0xff));
        }
        return buffer.toString();
    }

    public static byte[] createByteArray(int... arg) {
        byte[] byteArray = new byte[arg.length];
        for (int i = 0; i < arg.length; i++) {
            byteArray[i] = (byte) arg[i];
        }
        return byteArray;
    }

    private static class LittleEndianByteUtils extends ByteUtils {

        private LittleEndianByteUtils() {
        }

        public int bytesToInt(byte[] bytes, int position, int length) {
            return bytesToInt(bytes, position, length, (length - 1) * 8, -8);
        }

        public int bytesToInt(byte[] bytes) {
            return bytesToInt(bytes, 0, bytes.length);
        }

    }

    private static class BigEndianByteUtils extends ByteUtils {

        private BigEndianByteUtils() {
        }

        public int bytesToInt(byte[] bytes, int position, int length) {
            return bytesToInt(bytes, position, length, 0, 8);
        }

        public int bytesToInt(byte[] bytes) {
            return bytesToInt(bytes, 0, bytes.length);
        }

    }



    public static ByteUtils newLittleEndianUtils() {
        return littleEndianUtils;
    }

    public static ByteUtils newBigEndianUtils() {
        return bigEndianUtils;
    }
}
