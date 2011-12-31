package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * The End Code Page (ECP) structured field ends the code page object.
 */
public class EndCodePage extends AbstractStructuredField {

    private final EndFieldName cpName;

    public EndCodePage(StructuredFieldIntroducer introducer, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer);
        cpName = new EndFieldName(params, "Cp500");
    }

    /**
     * The ECP structured field name, if supplied, must match the corresponding BCP structured field
     * name. In an ECP structured field, no name or a null name (any name with X'FFFF' in the first
     * two bytes) matches any name in the BCP structured field.
     *
     * @return the code page name
     */
    public String getCodePageName() {
        return cpName.getName();
    }

    /**
     * If the name begins with X'FFFF' this matches any name on the BeginCodePage field.
     *
     * @return if the name matches any
     */
    public boolean nameMatchesAny() {
        return cpName.matchesAny();
    }

    @Override
    public String toString() {
        return getType().getName() + " cpName=" + cpName.getName();
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("CodePageName", getCodePageName());
        return params;
    }
}
