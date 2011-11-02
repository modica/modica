package org.afpparser.afp.modca.triplets;

import org.afpparser.afp.modca.TripletIdentifiers;

/**
 * A triplets is a self-identifying parameter that contains three components, the length of the
 * triplet, the ID identifying a triplet and the associated parameters. Triplets are also
 * responsible for parsing and instantiating themselves.
 */
public interface Triplet {

    int getLength();

    TripletIdentifiers getTid();
}
