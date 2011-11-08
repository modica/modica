package org.afpparser.afp.modca;

/**
 * The type of structured field.
 */
public interface SfType {
    /**
     * The category code.
     * @return the category
     */
    CategoryCode getCategoryCode();

    /**
     * The type code of this structured field type.
     *
     * @return the type code
     */
    TypeCode getTypeCode();

    /**
     * The name of this structured field type.
     *
     * @return the name
     */
    String getName();
}
