package org.afpparser.afp.modca;

import java.io.UnsupportedEncodingException;

import org.afpparser.common.ByteUtils;
import org.afpparser.common.StringUtils;

/**
 * This class is a wrapper for the structured field parameters data byte array. This keeps track of
 * where in the data byte array is being read, and every time a byte is read the pointer is
 * incremented.
 */
public class Parameters {

    private final byte[] sfData;
    private int position = 0;
    private static final ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();

    public Parameters(byte[] sfData, String stringEncoding) {
        this.sfData = sfData;
    }

    /**
     * Returns an unsigned integer.
     *
     * @param numberOfBytes the number of bytes that comprise the unsigned integer
     * @return an unsigned integer
     */
    public int getUInt(int numberOfBytes) {
        int number = byteUtils.bytesToUnsignedInt(sfData, position, numberOfBytes);
        position += numberOfBytes;
        return number;
    }

    /**
     * Returns a signed integer.
     *
     * @param numberOfBytes the number of bytes that comprise the signed integer
     * @return a signed integer
     */
    public int getInt(int numberOfBytes) {
        int number = byteUtils.bytesToSignedInt(sfData, position, numberOfBytes);
        position += numberOfBytes;
        return number;
    }

    /**
     * Returns a single byte.
     *
     * @return a byte
     */
    public byte getByte() {
        return sfData[position++];
    }

    /**
     * Returns a byte without moving the pointer in the data array.
     *
     * @return a byte
     */
    public byte peekByte() {
        return sfData[position];
    }

    /**
     * Returns a byte array from a given position, with length bytes. The position pointer is
     * unchanged with this method.
     *
     * @param position the starting position within the data array
     * @param length the number of bytes in the returned array
     * @return a byte array
     */
    public byte[] getByteArray(int position, int length) {
        byte[] data = new byte[length];
        System.arraycopy(sfData, position, data, 0, length);
        return data;
    }

    /**
     * Returns a byte array from the current position, with length bytes.
     *
     * @param length the number of bytes in the returned array
     * @return a byte array
     */
    public byte[] getByteArray(int length) {
        byte[] data = getByteArray(position, length);
        position += length;
        return data;
    }

    /**
     * Returns a string representation encoded with the encoding in the context object from the
     * given position, for length bytes. The file pointer isn't changed with this method call.
     *
     * @param position the starting position to encode the returned String
     * @param length the number of bytes in the returned String
     * @return the encoded String
     * @throws UnsupportedEncodingException if for some reason Cp500 encoding isn't supported
     */
    public String getString(int position, int length) throws UnsupportedEncodingException {
        return getString(position, length, "Cp500");
    }

    /**
     * Returns a string representation encoded with the encoding in the context object from the
     * current position, for length bytes.
     *
     * @param length the number of bytes in the returned String
     * @return the encoded String
     * @throws UnsupportedEncodingException if for some reason Cp500 encoding isn't supported
     */
    public String getString(int length) throws UnsupportedEncodingException {
        return getString(length, "Cp500");
    }

    /**
     * Returns a string representation given the encoding of the bytes from the given position, for
     * length bytes. The file pointer isn't changed with this method call.
     *
     * @param position the starting position to encode the returned String
     * @param length the number of bytes in the returned String
     * @param encoding the encoding scheme of the bytes
     * @return the encoded String
     * @throws UnsupportedEncodingException if for some reason the encoding isn't supported
     */
    public String getString(int position, int length, String encoding)
            throws UnsupportedEncodingException {
        if (position + length > size()) {
            return null;
        }
        return StringUtils.bytesToString(sfData, position, length, encoding);
    }

    /**
     * Returns a string representation encoded with a given encoding of the bytes from the current
     * position, for length bytes.
     *
     * @param length the number of bytes in the returned String
     * @param encoding the encoding scheme of the bytes
     * @return the encoded String
     * @throws UnsupportedEncodingException if for some reason the encoding isn't supported
     */
    public String getString(int length, String encoding) throws UnsupportedEncodingException {
        String str = getString(position, length, encoding);
        position += str != null ? length : 0;
        return str;
    }

    /**
     * Moves the position in the data array by a given number.
     *
     * @param bytes the number of bytes to skip
     */
    public void skip(int bytes) {
        skipTo(position + bytes);
    }

    /**
     * Moves the position in the data array to the position given.
     *
     * @param position the position to move to
     */
    public void skipTo(int position) {
        if (position > sfData.length) {
            throw new IllegalArgumentException("Cannot skip beyond the size of the byte array.");
        }
        if (position < 0) {
            throw new IllegalArgumentException("Cannot skip to a negative index.");
        }
        this.position = position;
    }

    /**
     * The number of bytes in the data array.
     *
     * @return the size of the data array
     */
    public int size() {
        return sfData.length;
    }

    /**
     * The current position in the data array.
     *
     * @return the position in the data array
     */
    public int getPosition() {
        return position;
    }

    /**
     * Returns the number of bytes left in the data array.
     *
     * @return the number of bytes until the end
     */
    public int bytesRemaining() {
        return size() - getPosition();
    }

    @Override
    public String toString() {
        return "pos=" + position + " size=" + sfData.length;
    }
}
