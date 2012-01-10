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

    private final byte[] comment;

    public NoOperation(StructuredFieldIntroducer introducer, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer);
        comment = params.getByteArray(params.size());
    }

    /**
     * The comment within the NoOp.
     *
     * @param encoding the encoding scheme of the comment
     * @return the comment
     * @throws UnsupportedEncodingException
     */
    public String getComment(String encoding) throws UnsupportedEncodingException {
        return new String(comment, encoding);
    }

    @Override
    public String toString() {
        return getType().getName();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        try {
            params.add(new ParameterAsString("Comment", getComment("Cp500")));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
        return params;
    }
}
