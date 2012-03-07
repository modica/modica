package org.modica.afp.modca.triplets;

import java.util.List;

import org.modica.afp.modca.ParameterAsString;

/**
 * A triplets is a self-identifying parameter that contains three components, the length of the
 * triplet, the ID identifying a triplet and the associated parameters. Triplets are also
 * responsible for parsing and instantiating themselves.
 */
public abstract class Triplet {

    /**
     * Returns the length of this triplet.
     *
     * @return the length of the triplet
     */
    public abstract int getLength();

    /**
     * Returns the triplet identifier.
     *
     * @return the triplet identifier
     */
    public abstract TripletIdentifiers getTid();

    /**
     * The value of the triplet as a String.
     *
     * @return the string representation of this triplet
     */
    public abstract List<ParameterAsString> getParameters();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}
