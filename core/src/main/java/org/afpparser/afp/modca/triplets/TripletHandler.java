package org.afpparser.afp.modca.triplets;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedName;
import org.afpparser.common.ByteUtils;

public class TripletHandler {

    private static final ByteUtils byteUtils = ByteUtils.newLittleEndianUtils();

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

    public static List<Triplet> parseTriplet(byte[] data, int offset) throws MalformedURLException,
            UnsupportedEncodingException {
        return parseTriplet(data, offset, data.length - offset);
    }

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
