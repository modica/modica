package org.afpparser.afp.modca;

import org.afpparser.afp.modca.SfTypeFactory.Begin;
import org.afpparser.afp.modca.SfTypeFactory.Map;

/**
 * An interface for factories that create {@link StructuredField} objects given a
 * {@link SfIntroducer} with the data pay-load.
 */
public interface StructuredFieldFactory {

    /**
     * Creates a structured field of the {@link Begin} type.
     *
     * @param introducer the introducer for the structured field
     * @param payload the structured field data
     * @return a Begin type structured field
     */
    StructuredField createBegin(SfIntroducer introducer, byte[] payload);

    /**
     * Creates a structured field of the {@link Map} type.
     *
     * @param introducer the introducer for the structured field
     * @param payload the structured field data
     * @return a Map type structured field
     */
    StructuredField createMap(SfIntroducer introducer, byte[] payload);
}
