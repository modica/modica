package org.afpparser.common;

public class ByteUtils {

    private ByteUtils() {
    }

    public static int bytesToInt(byte[] bytes, int position, int numberOfBytes) {
        int num = 0;
        if (numberOfBytes > 4) {
            throw new IllegalArgumentException("The maximum capacity for an int is 32 bytes");
        }
        for (int i = 0, bitShift = (numberOfBytes - 1) * 8; i < numberOfBytes; i++, bitShift -= 8) {
            num += (bytes[position + i] & 0xff) << bitShift;
        }
        return num;
    }

    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0, bytes.length);
    }

    public static byte[] createByteArray(int... arg) {
        byte[] byteArray = new byte[arg.length];
        for (int i = 0; i < arg.length; i++) {
            byteArray[i] = (byte) arg[i];
        }
        return byteArray;
    }
}
