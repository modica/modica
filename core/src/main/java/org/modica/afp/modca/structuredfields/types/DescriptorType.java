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
import org.modica.afp.modca.structuredfields.descriptor.CodePageDescriptor.CPDBuilder;
import org.modica.afp.modca.structuredfields.descriptor.FontDescriptor.FNDBuilder;
import org.modica.afp.modca.structuredfields.descriptor.ImageDataDescriptor.IDDBuilder;
import org.modica.afp.modca.structuredfields.descriptor.ObjectAreaDescriptor.OBDBuilder;
import org.modica.afp.modca.structuredfields.descriptor.PageDescriptor.PGDBuilder;
import org.modica.afp.modca.structuredfields.migration.PresentationTextDataDescriptor.PTDBuilder;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * A descriptor structured field defines the initial characteristics and, optionally, the formatting
 * directives for all objects, object areas, and pages. Depending on the specific descriptor
 * structured field type, it may contain some set of parameters that identify:
 * <ul>
 * <li>The size of the page or object</li>
 * <li>Measurement units</li>
 * <li>Initial presentation conditions</li>
 * </ul>
 */
public enum DescriptorType implements StructuredFieldType {
    /** Object Area Descriptor */
    OBD(CategoryCode.object_area, "Object Area Descriptor", new OBDBuilder()),
    /** IM Image Input Descriptor (C) */
    IID(CategoryCode.im_image, "IM Image Input Descriptor (C)", new NotYetImplementedBuilder()),
    /** Code Page Descriptor */
    CPD(CategoryCode.code_page, "Code Page Descriptor", new CPDBuilder()),
    /** Medium Descriptor */
    MDD(CategoryCode.medium, "Medium Descriptor", new NotYetImplementedBuilder()),
    /** Font Descriptor */
    FND(CategoryCode.font, "Font Descriptor", new FNDBuilder()),
    /** Container Data Descriptor */
    CDD(CategoryCode.object_container, "Container Data Descriptor", new NotYetImplementedBuilder()),
    /** Presentation Text Descriptor Format-1 (C) */
    PTD1(CategoryCode.presentation_text, "Presentation Text Descriptor Format-1 (C)", new PTDBuilder()),
    /** Page Descriptor */
    PGD(CategoryCode.page, "Page Descriptor", new PGDBuilder()),
    /** Graphics Data Descriptor */
    GDD(CategoryCode.graphics, "Graphics Data Descriptor", new NotYetImplementedBuilder()),
    /** Form Environment Group Descriptor (O) */
    FGD(CategoryCode.form_environment_group, "Form Environment Group Descriptor (O)", new NotYetImplementedBuilder()),
    /** Bar Code Data Descriptor */
    BDD(CategoryCode.barcode, "Bar Code Data Descriptor", new NotYetImplementedBuilder()),
    /** Image Data Descriptor */
    IDD(CategoryCode.image, "Image Data Descriptor", new IDDBuilder());

    public static final TypeCode TYPE_CODE = TypeCode.Descriptor;
    private final CategoryCode category;
    private final String name;
    private final Builder builder;

    private DescriptorType(CategoryCode category, String name, Builder builder) {
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