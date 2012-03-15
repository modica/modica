package org.modica.afp.modca;

import org.modica.afp.modca.structuredfields.StructuredField;

/**
 * This class provides context for structured fields. Some fields rely on parameters in other
 * structured fields to contextualize their purpose, this is an interface for providing that
 * context. The contextual data is used only to parse and instantiate the {@link StructuredField}.  
 */
public interface Context {

    /**
     * An enumeration of the FOCA context items.
     */
    public enum FOCAContext {
        CPI_REPEATING_GROUP_LENGTH;
    }

    /**
     * An enumeration of MODCA context items.
     */
    public enum MODCAContext {
        GCSGID;
    }

    /**
     * Put a FOCA related context item into the context map.
     *
     * @param focaContext the FOCA context item
     * @param obj the value of the item
     */
    public void put(FOCAContext focaContext, Object obj);

    /**
     * Put a MODCA related context item into the context map.
     *
     * @param modcaContext the MODCA context item
     * @param obj the value of the item
     */
    public void put(MODCAContext modcaContext, Object obj);

    /**
     * Get a FOCA related context item.
     *
     * @param focaContext the FOCA context item
     * @return the value of the item
     */
    public Object get(FOCAContext focaContext);

    /**
     * Get a MODCA related context item.
     *
     * @param modcaContext the MODCA context item
     * @return the value of the item
     */
    public Object get(MODCAContext modcaContext);
}
