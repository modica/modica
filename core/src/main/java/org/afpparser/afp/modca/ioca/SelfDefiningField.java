package org.afpparser.afp.modca.ioca;

/**
 * An interface for self defining fields.
 */
public interface SelfDefiningField {

    /**
     * The length of this field, including the length field but NOT including the identifier field.
     *
     * @return the length of this field
     */
    public int getLength();

    /**
     * The byte identifying this type of self identifying field.
     *
     * @return the byte id
     */
    public byte getId();
}
