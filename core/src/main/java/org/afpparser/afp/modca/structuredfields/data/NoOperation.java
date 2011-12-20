package org.afpparser.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;

/**
 * The No Operation field performs no function.
 */
public class NoOperation extends AbstractStructuredField {

    private final byte[] comment;

    public NoOperation(SfIntroducer introducer, Parameters params)
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
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        try {
            params.put("Comment", getComment("Cp500"));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
        return params;
    }
}
