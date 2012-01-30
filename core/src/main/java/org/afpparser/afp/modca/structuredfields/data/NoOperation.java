package org.afpparser.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * The No Operation field performs no function.
 */
public class NoOperation extends AbstractStructuredField {

    private final String comment;

    public NoOperation(StructuredFieldIntroducer introducer, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer);
        comment = params.getString(params.size());
    }

    /**
     * The comment within the NoOp.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("Comment", comment));
        return params;
    }
}
