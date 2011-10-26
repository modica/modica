package org.afpparser.common;

public class ByteUtils {

    private ByteUtils() {
    }

    public static int bytesToInt(byte[] bytes, int position, int numberOfBytes) {
        int num = 0;
        if (numberOfBytes > 4) {
            throw new IllegalArgumentException("The maximum capacity for an int is 32 bytes");
        }
        for (int i = numberOfBytes, index = position; i >= 0; i--, index++) {
            num += bytes[index] << (i * 4);
        }
        return num;
    }

    public static byte[] createByteArray(int... arg) {
        byte[] byteArray = new byte[arg.length];
        for (int i = 0; i < arg.length; i++) {
            byteArray[i] = (byte) arg[i];
        }
        return byteArray;
    }
}
