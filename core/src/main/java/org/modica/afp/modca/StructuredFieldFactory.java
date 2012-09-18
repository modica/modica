package org.modica.afp.modca;

import org.modica.afp.modca.structuredfields.AttributeType;
import org.modica.afp.modca.structuredfields.BeginType;
import org.modica.afp.modca.structuredfields.ControlType;
import org.modica.afp.modca.structuredfields.CopyCountType;
import org.modica.afp.modca.structuredfields.DescriptorType;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Data;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.End;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Include;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Index;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Link;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Map;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Migration;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Orientation;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Position;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Process;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Table;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Variable;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * An interface for factories that create {@link StructuredField} objects given a
 * {@link StructuredFieldIntroducer} with the data pay-load.
 */
public interface StructuredFieldFactory {

    /**
     * Creates a structured field of the {@link BeginType} type.
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
     * Creates a structured field of the {@link DescriptorType} type.
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
     * Creates a structured field of the {@link ControlType} type.
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
     * Creates a structured field of the {@link AttributeType} type.
     *
     * @param introducer the introducer for the structured field
     * @return an Attribute type structured field
     */
    StructuredField createAttribute(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link CopyCountType} type.
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
