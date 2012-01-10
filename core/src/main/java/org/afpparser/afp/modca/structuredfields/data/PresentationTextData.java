package org.afpparser.afp.modca.structuredfields.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.ptoca.ControlSequence;
import org.afpparser.afp.ptoca.ControlSequenceParser;

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
    public String toString() {
        return "PTX";
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        for (ControlSequence cs : ptocaData) {
            params.add(new ParameterAsString(cs.getControlSequenceIdentifier().getName(),
                    cs.getValueAsString()));
        }
        return params;
    }
}
