package org.modica.afp.modca;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class provides context for structured fields. Some fields rely on parameters in other
 * structured fields to contextualize their purpose, this class provides that context.
 */
public class ContextImpl implements Context {

    private final Map<MODCAContext, Object> modcaContextItems = new EnumMap<MODCAContext, Object>(MODCAContext.class);
    private final Map<FOCAContext, Object> focaContextItems = new EnumMap<FOCAContext, Object>(FOCAContext.class);

    public ContextImpl() {
        modcaContextItems.put(MODCAContext.GCSGID, "Cp500");
    }

    /**
     * Put a FOCA related context item into the context map.
     *
     * @param focaContext the FOCA context item
     * @param obj the value of the item
     */
    public void put(FOCAContext focaContext, Object obj) {
        focaContextItems.put(focaContext, obj);
    }

    /**
     * Put a MODCA related context item into the context map.
     *
     * @param modcaContext the MODCA context item
     * @param obj the value of the item
     */
    public void put(MODCAContext modcaContext, Object obj) {
        modcaContextItems.put(modcaContext, obj);
    }

    /**
     * Get a FOCA related context item.
     *
     * @param focaContext the FOCA context item
     * @return the value of the item
     */
    public Object get(FOCAContext focaContext) {
        return focaContextItems.get(focaContext);
    }

    /**
     * Get a MODCA related context item.
     *
     * @param modcaContext the MODCA context item
     * @return the value of the item
     */
    public Object get(MODCAContext modcaContext) {
        return modcaContextItems.get(modcaContext);
    }
}
