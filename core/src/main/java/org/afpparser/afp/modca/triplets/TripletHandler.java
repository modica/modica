package org.afpparser.afp.modca.triplets;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedName;
import org.afpparser.common.ByteUtils;

/**
 * A handler for parsing Structured Field Triplets and Triplet groups.
 */
public final class TripletHandler {

    private TripletHandler() {
    }

    private static final ByteUtils byteUtils = ByteUtils.newLittleEndianUtils();

    /**
     * Parse the triplet from the byte array starting at offset reading length bytes.
     *
     * @param data the triplet data
     * @param offset the byte offset in the byte array
     * @param length the number of bytes to read
     * @return A List of triplets
     * @throws MalformedURLException if an error was thrown parsing a URL
     * @throws UnsupportedEncodingException if an error was thrown decoding a String
     */
    public static List<Triplet> parseTriplet(byte[] data, int offset, int length)
            throws MalformedURLException, UnsupportedEncodingException {
        int position = offset;
        List<Triplet> tripletList = new ArrayList<Triplet>();
        // The length field of the recurring group is included in the
        int reoccuringGroup = offset + length - 2;
        while (position < reoccuringGroup) {
            int tripletLength = data[position++] & 0xFF;
            TripletIdentifiers tId = TripletIdentifiers.getTripletId(data[position]);
            switch (tId) {
            case fully_qualified_name:
                tripletList.add(FullyQualifiedName.parse(data, position, tripletLength));
                break;
            case character_rotation:
                tripletList.add(new CharacterRotation(data, position));
                break;
            }
            position += tripletLength - 1; // The length field is included in the triplet length
        }
        return tripletList;
    }

    /**
     * Parse the triplet from the byte array starting at offset reading the remainder of bytes in
     * the data array i.e. (data.length - offset).
     *
     * @param data the triplet data
     * @param offset the byte offset in the byte array
     * @return A List of triplets
     * @throws MalformedURLException if an error was thrown parsing a URL
     * @throws UnsupportedEncodingException if an error was thrown decoding a String
     */
    public static List<Triplet> parseTriplet(byte[] data, int offset) throws MalformedURLException,
            UnsupportedEncodingException {
        return parseTriplet(data, offset, data.length - offset);
    }

    /**
     * Parse the repeating group of triplets from the data array given.
     *
     * @param data the triplet group data
     * @return A {@link RepeatingTripletGroup}
     * @throws MalformedURLException if an error was thrown parsing a URL
     * @throws UnsupportedEncodingException if an error was thrown decoding a String
     */
    public static RepeatingTripletGroup parseRepeatingGroup(byte[] data)
            throws MalformedURLException, UnsupportedEncodingException {
        int byteIndex = 0;
        List<List<Triplet>> repeatingTriplets = new ArrayList<List<Triplet>>();

        while (byteIndex < data.length) {
            int rgLength = byteUtils.bytesToUnsignedInt(data, byteIndex, 2);
            List<Triplet> triplets = TripletHandler.parseTriplet(data, 2, rgLength);
            repeatingTriplets.add(triplets);
            byteIndex += rgLength;
        }
        return new RepeatingTripletGroup(repeatingTriplets);
    }
}