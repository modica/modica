package org.afpparser.afp.modca.triplets;


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
}
