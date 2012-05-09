package org.modica.afp.modca;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.modica.afp.modca.structuredfields.descriptor.CodePageDescriptor;
import org.modica.afp.modca.structuredfields.map.MapCodedFont;
import org.modica.afp.modca.structuredfields.map.MapCodedFont.CharacterSetCodePage;

/**
 * This class provides context for structured fields. Some fields rely on parameters in other
 * structured fields to contextualize their purpose, this class provides that context.
 */
public class Context {

    private final Map<ContextType, Object> contextObjs = new EnumMap<ContextType, Object>(ContextType.class);
    private final Map<String, CodePageDescriptor> codePages = new HashMap<String, CodePageDescriptor>();
    private String currentCodePage;

    public Context() {
        contextObjs.put(ContextType.MODCA_GCSGID, "Cp500");
    }

    public enum ContextType {
        FOCA_CPI_REPEATING_GROUP_LENGTH,
        MODCA_GCSGID,
        MODCA_MAP_CODED_FONT,
        PTOCA_SET_CODED_FONT_LOCAL;
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

    public void setCurrentCodePageName(String name) {
        if (currentCodePage != null) {
            throw new IllegalStateException("Trying to start a push the code page:" + name
                    + " into the context stack while " + currentCodePage + " hasn't been finished");
        }
        currentCodePage = name;
    }

    public void setCpgidForCodePage(CodePageDescriptor descriptor) {
        if (currentCodePage == null) {
            throw new IllegalStateException("There is no code page name set for this CodePageDescriptor");
        }
        codePages.put(currentCodePage, descriptor);
    }

    public void endCodePage() {
        currentCodePage = null;
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

    public int getPTXEncoding() {
        byte b = (Byte) contextObjs.get(ContextType.PTOCA_SET_CODED_FONT_LOCAL);
        MapCodedFont mcf = (MapCodedFont) contextObjs.get(ContextType.MODCA_MAP_CODED_FONT);
        CharacterSetCodePage cscp = mcf.getFontMappings(b);
        CodePageDescriptor descriptor = codePages.get(cscp.getCodePage());
        // TODO: I really odn't like this!! We need to find a better way of handling strings
        return descriptor == null ? 500 : descriptor.getCpgid();
    }
}
