package org.afpparser.afp.modca;

import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Control;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Data;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Descriptor;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.End;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Include;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Index;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Map;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Migration;
import org.afpparser.afp.modca.structuredfields.SfTypeFactory.Position;
import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 * An interface for factories that create {@link StructuredField} objects given a
 * {@link StructuredFieldIntroducer} with the data pay-load.
 */
public interface StructuredFieldFactory {

    /**
     * Creates a structured field of the {@link Begin} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Begin type structured field
     */
    StructuredField createBegin(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Map} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createMap(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Descriptor} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createDescriptor(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Migration} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createMigration(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link End} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createEnd(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Data} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createData(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Position} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createPosition(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Include} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createInclude(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Control} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createControl(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Index} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Map type structured field
     */
    StructuredField createIndex(StructuredFieldIntroducer introducer);

}
