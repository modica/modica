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

package org.modica.afp.modca.structuredfields.types;

import org.modica.afp.modca.structuredfields.CategoryCode;
import org.modica.afp.modca.structuredfields.StructuredField.Builder;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldType;
import org.modica.afp.modca.structuredfields.TypeCode;
import org.modica.afp.modca.structuredfields.map.MapCodedFont.MCFBuilder;
import org.modica.afp.modca.structuredfields.map.MapImageObject.MIOBuilder;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * A map structured field provides the following functions in the MO:DCA architecture:
 * <ul>
 * <li>
 * All occurrences of a variable embedded in structured field parameter data can be given a new
 * value by changing only one reference in the mapping, rather than having to physically change each
 * occurrence. Thus all references to font X may cause a Times Roman font to be used in one instance
 * and a Helvetica font in another instance merely by specifying the proper map coded font
 * structured field.
 * </li>
 * <li>
 * The presence of the map structured field in a MO:DCA environment group indicates use of the
 * named resource within the scope of the environment group.
 * </li>
 * </ul>
 */
public enum MapType implements StructuredFieldType {
    /** Map Color Attribute Table */
    MCA(CategoryCode.color_attribute_table, "Map Color Attribute Table", new NotYetImplementedBuilder()),
    /** Map Media Type */
    MMT(CategoryCode.medium, "Map Media Type", new NotYetImplementedBuilder()),
    /** Font Name Map */
    FNN(CategoryCode.font, "Font Name Map", new NotYetImplementedBuilder()),
    /** Map Coded Font */
    MCF(CategoryCode.coded_font, "Map Coded Font", new MCFBuilder()),
    /** Map Container Data */
    MCD(CategoryCode.object_container, "Map Container Data", new NotYetImplementedBuilder()),
    /** Map Page */
    MPG(CategoryCode.page, "Map Page", new NotYetImplementedBuilder()),
    /** Map Graphics Object */
    MGO(CategoryCode.graphics, "Map Graphics Object", new NotYetImplementedBuilder()),
    /** Map Data Resource */
    MDR(CategoryCode.data_resource, "Map Data Resource", new NotYetImplementedBuilder()),
    /** Invoke Medium Map */
    IMM(CategoryCode.medium_map, "Invoke Medium Map", new NotYetImplementedBuilder()),
    /** Map Page Overlay */
    MPO(CategoryCode.page_overlay, "Map Page Overlay", new NotYetImplementedBuilder()),
    /** Map Suppression */
    MSU(CategoryCode.data_supression, "Map Suppression", new NotYetImplementedBuilder()),
    /** Map Bar Code Object */
    MBC(CategoryCode.barcode, "Map Bar Code Object", new NotYetImplementedBuilder()),
    /** Map Image Object */
    MIO(CategoryCode.image, "Map Image Object", new MIOBuilder());

    public static final TypeCode TYPE_CODE = TypeCode.Map;
    private final CategoryCode category;
    private final String name;
    private final Builder builder;

    private MapType(CategoryCode category, String name, Builder builder) {
        this.category = category;
        this.name = name;
        this.builder = builder;
    }

    @Override
    public CategoryCode getCategoryCode() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TypeCode getTypeCode() {
        return TYPE_CODE;
    }

    @Override
    public Builder getBuilder() {
        return builder;
    }

    @Override
    public void handleIntroducer(StructuredFieldIntroducerHandler handler,
            StructuredFieldIntroducer intro) {
        handler.handle(intro);
    }
}