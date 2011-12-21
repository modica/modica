package org.afpparser.afp.modca.structuredfields.data;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.ptoca.ControlSequence;
import org.afpparser.afp.ptoca.ControlSequenceParser;

/**
 * The Presentation Text Data structured field contains the data for a presentation text data
 * object.
 */
public class PresentationTextData extends AbstractStructuredField {

    private final List<ControlSequence> ptocaData;

    public PresentationTextData(SfIntroducer introducer, Parameters params) {
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
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        int counter = 0;
        for (ControlSequence cs : ptocaData) {
            params.put(counter++ + " " + cs.getControlSequenceIdentifier().getName(),
                    cs.getValueAsString());
        }
        return params;
    }
}
