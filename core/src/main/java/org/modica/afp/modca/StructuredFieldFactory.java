package org.modica.afp.modca;

import org.modica.afp.modca.structuredfields.SfTypeFactory.Attribute;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Begin;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Control;
import org.modica.afp.modca.structuredfields.SfTypeFactory.CopyCount;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Data;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Descriptor;
import org.modica.afp.modca.structuredfields.SfTypeFactory.End;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Include;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Index;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Link;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Map;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Migration;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Orientation;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Position;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Process;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Table;
import org.modica.afp.modca.structuredfields.SfTypeFactory.Variable;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

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
     * @return a Descriptor type structured field
     */
    StructuredField createDescriptor(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Migration} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Migration type structured field
     */
    StructuredField createMigration(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link End} type.
     *
     * @param introducer the introducer for the structured field
     * @return a End type structured field
     */
    StructuredField createEnd(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Data} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Data type structured field
     */
    StructuredField createData(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Position} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Position type structured field
     */
    StructuredField createPosition(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Include} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Include type structured field
     */
    StructuredField createInclude(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Control} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Control type structured field
     */
    StructuredField createControl(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Index} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Index type structured field
     */
    StructuredField createIndex(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Attribute} type.
     *
     * @param introducer the introducer for the structured field
     * @return an Attribute type structured field
     */
    StructuredField createAttribute(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link CopyCount} type.
     *
     * @param introducer the introducer for the structured field
     * @return a CopyCount type structured field
     */
    StructuredField createCopyCount(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Process} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Process type structured field
     */
    StructuredField createProcess(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Orientation} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Orientation type structured field
     */
    StructuredField createOrientation(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Table} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Table type structured field
     */
    StructuredField createTable(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Variable} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Variable type structured field
     */
    StructuredField createVariable(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link Link} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Link type structured field
     */
    StructuredField createLink(StructuredFieldIntroducer introducer);
}
