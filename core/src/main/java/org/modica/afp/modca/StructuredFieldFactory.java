/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.modica.afp.modca;

import org.modica.afp.modca.structuredfields.ControlType;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.types.AttributeType;
import org.modica.afp.modca.structuredfields.types.BeginType;
import org.modica.afp.modca.structuredfields.types.CopyCountType;
import org.modica.afp.modca.structuredfields.types.DataType;
import org.modica.afp.modca.structuredfields.types.DescriptorType;
import org.modica.afp.modca.structuredfields.types.EndType;
import org.modica.afp.modca.structuredfields.types.IncludeType;
import org.modica.afp.modca.structuredfields.types.IndexType;
import org.modica.afp.modca.structuredfields.types.LinkType;
import org.modica.afp.modca.structuredfields.types.MapType;
import org.modica.afp.modca.structuredfields.types.MigrationType;
import org.modica.afp.modca.structuredfields.types.OrientationType;
import org.modica.afp.modca.structuredfields.types.PositionType;
import org.modica.afp.modca.structuredfields.types.ProcessType;
import org.modica.afp.modca.structuredfields.types.TableType;
import org.modica.afp.modca.structuredfields.types.VariableType;

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
     * Creates a structured field of the {@link MapType} type.
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
     * Creates a structured field of the {@link MigrationType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Migration type structured field
     */
    StructuredField createMigration(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link EndType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a End type structured field
     */
    StructuredField createEnd(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link DataType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Data type structured field
     */
    StructuredField createData(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link PositionType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Position type structured field
     */
    StructuredField createPosition(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link IncludeType} type.
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
     * Creates a structured field of the {@link IndexType} type.
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
     * Creates a structured field of the {@link ProcessType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Process type structured field
     */
    StructuredField createProcess(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link OrientationType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Orientation type structured field
     */
    StructuredField createOrientation(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link TableType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Table type structured field
     */
    StructuredField createTable(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link VariableType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Variable type structured field
     */
    StructuredField createVariable(StructuredFieldIntroducer introducer);

    /**
     * Creates a structured field of the {@link LinkType} type.
     *
     * @param introducer the introducer for the structured field
     * @return a Link type structured field
     */
    StructuredField createLink(StructuredFieldIntroducer introducer);
}
