package org.modica.afp.modca.structuredfields;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField.Builder;

/**
 * This represents type of structured field as defined in the AFP specifications (MODCA, FOCA etc).
 */
public interface StructuredFieldType {

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

    /**
     * The builder for the structured field of this type.
     *
     * @return structured field builder
     */
    Builder getBuilder();

    public final static class NotYetImplementedBuilder implements Builder {
        @Override
        public StructuredField build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return null;
        }
    }
}
