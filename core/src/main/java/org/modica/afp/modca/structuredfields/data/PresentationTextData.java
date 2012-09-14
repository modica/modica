package org.modica.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.ptoca.ControlSequence;
import org.modica.afp.ptoca.ControlSequenceParser;

/**
 * The Presentation Text Data structured field contains the data for a presentation text data
 * object.
 */
public class PresentationTextData extends AbstractStructuredField {

    private final List<ControlSequence> ptocaData;

    public PresentationTextData(StructuredFieldIntroducer introducer, Parameters params,
            Context ctx) throws UnsupportedEncodingException {
        super(introducer);
        ptocaData = ControlSequenceParser.parse(params, ctx);
    }

    /**
     * Returns the PTOCA-defined control sequences that this object wraps.
     *
     * @return the PTOCA data
     */
    public List<ControlSequence> getPtocaData() {
        return Collections.unmodifiableList(ptocaData);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }

    public class PTXBuilder implements Builder {
        @Override
        public PresentationTextData create(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException {
            return new PresentationTextData(intro, params, context);
        }
    }
}
