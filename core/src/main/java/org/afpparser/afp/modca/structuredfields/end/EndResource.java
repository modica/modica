package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * The End Resource structured field terminates an envelope that is used to carry resource objects
 * in external (print-file-level) resource groups. The envelope is initiated by a Begin Resource
 * (BRS) structured field.
 */
public class EndResource extends AbstractStructuredField {

    private final EndFieldName rsName;

    public EndResource(StructuredFieldIntroducer introducer, Parameters params) throws UnsupportedEncodingException {
        super(introducer);
        rsName = new EndFieldName(params);
    }

    /**
     * Returns the name of the resource being terminated. If a name is specified, it must match the
     * name in the most recent Begin Resource structured field. If the first two bytes in RSName
     * contain the value X'FFFF', the name matches any name specified on the Begin Resource
     * structured field that initiated the current definition.
     *
     * @return the resource name
     */
    public String getRSName() {
        return rsName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the BeginResource
     * object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return rsName.matchesAny();
    }

    @Override
    public String toString() {
        return getType().getName() + " RSName=" + rsName;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("ResourceName", getRSName());
        return params;
    }
}
