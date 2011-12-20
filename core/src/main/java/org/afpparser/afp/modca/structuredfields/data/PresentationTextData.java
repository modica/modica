package org.afpparser.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.common.StringUtils;

/**
 * The Presentation Text Data structured field contains the data for a presentation text data
 * object.
 */
public class PresentationTextData extends AbstractStructuredField {

    private final byte[] ptocaData;

    public PresentationTextData(SfIntroducer introducer, Parameters params) {
        super(introducer);
        ptocaData = params.getByteArray(params.size());
    }

    /**
     * Returns the PTOCA-defined text descriptor encoded,
     *
     * @return the PTOCA data
     * @throws UnsupportedEncodingException if there was an encoding error
     */
    public String getPtocaData(String encoding) throws UnsupportedEncodingException {
        return new String(ptocaData, encoding);
    }

    /**
     * Returns the PTOCA-defined text descriptor encoded using the standard "Cp500" encoding.
     *
     * @return the PTOCA data
     * @throws UnsupportedEncodingException if there was an encoding error
     */
    public String getPtocaData() throws UnsupportedEncodingException {
        return StringUtils.bytesToCp500(ptocaData);
    }

    @Override
    public String toString() {
        return "PTX";
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        try {
            params.put("Text", getPtocaData());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return params;
    }
}
