package org.modica.afp.modca;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class provides context for structured fields. Some fields rely on parameters in other
 * structured fields to contextualize their purpose, this class provides that context.
 */
public class Context {

    private final Map<ContextType, Object> contextObjs = new EnumMap<ContextType, Object>(ContextType.class);

    public Context() {
        contextObjs.put(ContextType.MODCA_GCSGID, "Cp500");
    }

    public enum ContextType {
        FOCA_CPI_REPEATING_GROUP_LENGTH,
        MODCA_GCSGID,
        MODCA_MAP_CODED_FONT;
    }

    /**
     * Put a context item into the context map.
     *
     * @param the context item
     * @param obj the value of the item
     */
    public void put(ContextType contextType, Object obj) {
        contextObjs.put(contextType, obj);
    }

    /**
     * Get a FOCA related context item.
     *
     * @param focaContext the FOCA context item
     * @return the value of the item
     */
    public Object get(ContextType focaContext) {
        return contextObjs.get(focaContext);
    }
}
