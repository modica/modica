package org.afpparser.afp.modca;

import org.afpparser.afp.modca.SfTypeFactory.Begin;
import org.afpparser.afp.modca.SfTypeFactory.Map;

/**
 * An interface for factories that create {@link StructuredField} objects given
 * a {@link SfIntroducer} with the data pay-load.
 */
public interface StructuredFieldFactory {

    /**
     * Creates a structured field of the {@link Begin} type.
     * 
     * @param introducer
     *            the introducer for the structured field
     * @return a Begin type structured field
     */
    StructuredField createBegin(SfIntroducer introducer);

    /**
     * Creates a structured field of the {@link Map} type.
     * 
     * @param introducer
     *            the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createMap(SfIntroducer introducer);
}
