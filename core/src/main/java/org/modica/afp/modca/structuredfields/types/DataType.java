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
import org.modica.afp.modca.structuredfields.data.ImagePictureData.IPDBuilder;
import org.modica.afp.modca.structuredfields.data.NoOperation.NOPBuilder;
import org.modica.afp.modca.structuredfields.data.PresentationTextData.PTXBuilder;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * A data structured field consists of data whose meaning and interpretation is governed by the
 * object architecture for the particular data object type.
 */
public enum DataType implements StructuredFieldType {
    /** IM Image Raster Data (C) */
    IRD(CategoryCode.im_image, "IM Image Raster Data (C)", new NotYetImplementedBuilder()),
    /** Font Patterns */
    FNG(CategoryCode.font, "Font Patterns", new NotYetImplementedBuilder()),
    /** Object Container Data */
    OCD(CategoryCode.object_container, "Object Container Data", new NotYetImplementedBuilder()),
    /** Presentation Text Data */
    PTX(CategoryCode.presentation_text, "Presentation Text Data", new PTXBuilder()),
    /** Graphics Data */
    GAD(CategoryCode.graphics, "Graphics Data", new NotYetImplementedBuilder()),
    /** Bar Code Data */
    BDA(CategoryCode.barcode, "Bar Code Data", new NotYetImplementedBuilder()),
    /** No Operation */
    NOP(CategoryCode.no_operation, "No Operation", new NOPBuilder()),
    /** Image Picture Data */
    IPD(CategoryCode.image, "Image Picture Data", new IPDBuilder()),
    // CTX is deprecated from the MO:DCA spec
    /** Composed Text Data */
    CTX(CategoryCode.presentation_text, "Composed Text Data", new NotYetImplementedBuilder());

    private static final TypeCode TYPE_CODE = TypeCode.Data;
    private final CategoryCode category;
    private final String name;
    private final Builder builder;

    private DataType(CategoryCode category, String name, Builder builder) {
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