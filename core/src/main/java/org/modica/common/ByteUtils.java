/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.modica.common;

/** A utility class to assist with binary operations. */
public abstract class ByteUtils {

    private static final ByteUtils littleEndianUtils = new LittleEndianByteUtils();
    private static final ByteUtils bigEndianUtils = new BigEndianByteUtils();
    private static final char[] HEX = "0123456789abcdef".toCharArray();

    private ByteUtils() {
    }

    /**
     * Takes an array of bytes within <code>bytes</code> starting at <code>position</code> with
     * <code>length</code> number of bytes and returns the <code>int</code> representation. An
     * integer primitive is 32bits so this can take up to 4 bytes as the length. The integer
     * returned will be unsigned, thus always positive.
     *
     * @param bytes the byte array to extract the integer from
     * @param position the byte offset within <code>bytes</code>
     * @param length the number of bytes to create the integer from
     * @return an unsigned integer
     */
    public abstract long bytesToUnsignedInt(byte[] bytes, int position, int length);

    /**
     * Takes an array of bytes within <code>bytes</code> starting at <code>position</code> with
     * <code>length</code> number of bytes and returns the <code>int</code> representation. An
     * integer primitive is 32bits so this can take up to 4 bytes as the length. The integer
     * returned will be signed.
     *
     * @param bytes the byte array to extract the integer from
     * @param position the byte offset within <code>bytes</code>
     * @param length the number of bytes to create the integer from
     * @return a signed integer
     */
    public abstract int bytesToSignedInt(byte[] bytes, int position, int length);

    /**
     * Takes an array of bytes and starting at the zeroth offset for <code>bytes.length</code> bytes
     * returns the <code>int</code> representation. An integer primitive is 32bits so this can take
     * up to 4 bytes as the length. The integer returned will be unsigned, thus always positive.
     *
     * @param bytes the byte array to extract the integer from
     * @return an unsigned integer
     */
    public abstract long bytesToUnsignedInt(byte[] bytes);

    private static final long bytesToUInt(byte[] bytes, int position, int length,
            int bitShiftStart, int bitShitIncrement) {
        long num = 0;
        if (length > 4) {
            throw new IllegalArgumentException("The maximum capacity for an int is 32 bytes");
        }
        for (int i = 0, bitShift = bitShiftStart; i < length; i++, bitShift += bitShitIncrement) {
            long tempVal = (bytes[position + i] & 0xff);
            num += tempVal << bitShift;
        }
        return num;
    }

    private static final int bytesToInt(byte[] bytes, int position, int length, int bitShiftStart,
            int bitShitIncrement) {
        if (length > 4) {
            throw new IllegalArgumentException("The maximum capacity for an int is 32 bytes");
        }
        int signum = bytes[position] < 0 ? -1 : 1;
        int positiveNum = (int) bytesToUInt(bytes, position, length, bitShiftStart,
                bitShitIncrement);
        return signum * positiveNum;
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
            data[i] = (byte) ((Character.digit(hexArray[charIndex], 16) << 4) + (Character.digit(
                    hexArray[charIndex + 1], 16)));
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
    public static final String bytesToHex(byte[] bytes, int position, int length) {
        char[] chars = new char[length * 2];
        int charIndex = 0;
        for (int i = position; i < position + length; i++) {
            chars[charIndex++] = HEX[(bytes[i] & 0xf0) >>> 4];
            chars[charIndex++] = HEX[(bytes[i] & 0x0f)];
        }
        return String.valueOf(chars);
    }

    /**
     * Converts all the bytes given to a hexadecimal string.
     *
     * @param bytes the bytes to convert to hex
     * @return the hexadecimal string
     */
    public static final String bytesToHex(byte... bytes) {
        return bytesToHex(bytes, 0, bytes.length);
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
            byteArray[i] = (byte) (element[i] & 0xFF);
        }
        return byteArray;
    }

    /**
     * Checks that the byte array given matches the series of elements given as the varargs
     * parameters. This returns false only if any elements are different, it does not check the
     * length of the byte array.
     *
     * @param array the byte array
     * @param elements the byte elements to compare
     * @return true if the subsets are equal
     */
    public static boolean arrayEqualsSubset(byte[] array, int... elements) {
        try {
            for (int i = 0; i < elements.length; i++) {
                if (array[i] != (byte) elements[i]) {
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private static class LittleEndianByteUtils extends ByteUtils {

        private LittleEndianByteUtils() {
        }

        @Override
        public long bytesToUnsignedInt(byte[] bytes, int position, int length) {
            return bytesToUInt(bytes, position, length, 0, 8);
        }

        @Override
        public long bytesToUnsignedInt(byte[] bytes) {
            return bytesToUnsignedInt(bytes, 0, bytes.length);
        }
        @Override
        public int bytesToSignedInt(byte[] bytes, int position, int length) {
            return bytesToInt(bytes, position, length, 0, 8);
        }

    }

    private static class BigEndianByteUtils extends ByteUtils {

        private BigEndianByteUtils() {
        }

        @Override
        public long bytesToUnsignedInt(byte[] bytes, int position, int length) {
            return bytesToUInt(bytes, position, length, (length - 1) * 8, -8);
        }

        @Override
        public long bytesToUnsignedInt(byte[] bytes) {
            return bytesToUnsignedInt(bytes, 0, bytes.length);
        }

        @Override
        public int bytesToSignedInt(byte[] bytes, int position, int length) {
            return bytesToInt(bytes, position, length, (length - 1) * 8, -8);
        }
    }

    /**
     * Returns a a little endian binary utility.
     *
     * @return a little endian binary assistant
     */
    public static ByteUtils getLittleEndianUtils() {
        return littleEndianUtils;
    }

    /**
     * Returns a big endian binary utility.
     *
     * @return a big endian binary assitant
     */
    public static ByteUtils getBigEndianUtils() {
        return bigEndianUtils;
    }
}
