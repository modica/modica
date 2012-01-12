package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * The End Object Environment Group structured field terminates the definition of an Object
 * Environment Group initiated by a Begin Object Environment Group structured field.
 */
public class EndObjectEnvironmentGroup extends AbstractStructuredField {

    private final EndFieldName oegName;

    public EndObjectEnvironmentGroup(StructuredFieldIntroducer introducer, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer);
        oegName = new EndFieldName(params);
    }

    /**
     * Returns the name of the object environment group that is being terminated. If a name is
     * specified, it must match the name in the most recent Begin Object Environment Group
     * structured field in the object or a X’01’ exception condition exists. If the first two bytes
     * of OEGName contain the value X'FFFF', the name matches any name specified on the Begin Object
     * Environment Group structured field that initiated the current definition.
     *
     * @return the End Environment Group name
     */
    public String getOegName() {
        return oegName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the
     * BeginObjectEnvironmentGroup object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return oegName.matchesAny();
    }

    @Override
    public String toString() {
        return getType().getName() + " OEGName=" + getOegName();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ObjectEnvironmentGroupName", getOegName()));
        return params;
    }
}
