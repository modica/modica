package org.modica.afp.modca.structuredfields;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField.Builder;
import org.modica.parser.StructuredFieldIntroducerHandler;

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
     * Delegates to the appropriate method on the handler for dealing with this structured field.
     *
     * @param handler the handler
     */
    void handleIntroducer(StructuredFieldIntroducerHandler handler, StructuredFieldIntroducer intro);

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
