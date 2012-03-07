package org.modica.afp.modca.structuredfields.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public PresentationTextData(StructuredFieldIntroducer introducer, Parameters params) {
        super(introducer);
        ptocaData = ControlSequenceParser.parse(params);
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
}
