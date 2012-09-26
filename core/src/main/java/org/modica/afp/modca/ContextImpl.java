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
public class ContextImpl implements Context {

    private final Map<ContextType, Object> contextObjs = new EnumMap<ContextType, Object>(ContextType.class);
    private final Map<String, CodePageDescriptor> codePages = new HashMap<String, CodePageDescriptor>();
    private String currentCodePageName;

    public ContextImpl() {
        contextObjs.put(ContextType.MODCA_GCSGID, EbcdicStringHandler.DEFAULT_CPGID);
    }

    @Override
    public void put(ContextType contextType, Object obj) {
        contextObjs.put(contextType, obj);
    }

    @Override
    public Object get(ContextType contextType) {
        return contextObjs.get(contextType);
    }

    @Override
    public void setCurrentCodePageName(String name) {
        if (currentCodePageName != null) {
            throw new IllegalStateException("Trying to start a push the code page:" + name
                    + " into the context stack while " + currentCodePageName + " hasn't been finished");
        }
        currentCodePageName = name;
    }

    @Override
    public void setCpgidForCodePage(CodePageDescriptor descriptor) {
        if (currentCodePageName == null) {
            throw new IllegalStateException("There is no code page name set for this CodePageDescriptor");
        }
        codePages.put(currentCodePageName, descriptor);
        // reset the current code page so that we can make state checks
        currentCodePageName = null;
    }

    @Override
    public void endCodePage() {
        currentCodePageName = null;
    }

    @Override
    public int getPTXEncoding() {
        byte b = (Byte) contextObjs.get(ContextType.PTOCA_SET_CODED_FONT_LOCAL);
        MapCodedFont mcf = (MapCodedFont) contextObjs.get(ContextType.MODCA_MAP_CODED_FONT);
        CharacterSetCodePage cscp = mcf.getFontMappings(b);
        CodePageDescriptor descriptor = codePages.get(cscp.getCodePage());
        // TODO: I really don't like this!! We need to find a better way of handling strings
        return descriptor == null ? EbcdicStringHandler.DEFAULT_CPGID : descriptor.getCpgid();
    }
}
