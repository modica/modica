package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

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
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ResourceName", getRSName()));
        return params;
    }
}
