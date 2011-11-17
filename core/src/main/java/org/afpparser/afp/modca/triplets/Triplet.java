package org.afpparser.afp.modca.triplets;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.triplets.fullyqualifiedname.FullyQualifiedName;



/**
 * A triplets is a self-identifying parameter that contains three components, the length of the
 * triplet, the ID identifying a triplet and the associated parameters. Triplets are also
 * responsible for parsing and instantiating themselves.
 */
public abstract class Triplet {

    public abstract int getLength();

    public abstract TripletIdentifiers getTid();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

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

            }
            position += tripletLength - 1; // The length field is included in the triplet length
        }
        return tripletList;
    }
}
