package org.afpparser.afp.modca;

/**
 * The type of structured field.
 */
public interface SfType {
    /**
     * The identity array representing this structure field type.
     *
     * @return the id array
     */
    byte[] getId();

    /**
     * The type code of this structured field type.
     *
     * @return the type code
     */
    TypeCodes getTypeCode();

    /**
     * The name of this structured field type.
     *
     * @return the name
     */
    String getName();
}
