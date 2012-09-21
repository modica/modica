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

package org.modica.afp.modca.structuredfields;

import org.modica.afp.modca.structuredfields.StructuredField.Builder;
import org.modica.afp.modca.structuredfields.control.CodePageControl.CPCBuilder;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * A control structured field specifies the type of modifications that are to be applied to a group
 * of sheet copies, or a copy subgroup.
 */
public enum ControlType implements StructuredFieldType {
    /** IM Image Output Control (C) */
    IOC(CategoryCode.im_image, "IM Image Output Control (C)", new NotYetImplementedBuilder()),
    /** Code Page Control */
    CPC(CategoryCode.code_page, "Code Page Control", new CPCBuilder()),
    /** Medium Modification Control */
    MMC(CategoryCode.medium, "Medium Modification Control", new NotYetImplementedBuilder()),
    /** Font Control */
    FNC(CategoryCode.font, "Font Control", new NotYetImplementedBuilder()),
    /** Coded Font Control */
    CFC(CategoryCode.coded_font, "Coded Font Control", new NotYetImplementedBuilder()),
    /** Composed Text Control (O) */
    CTC(CategoryCode.presentation_text, "Composed Text Control (O)", new NotYetImplementedBuilder()),
    /** Presentation Environment Control */
    PEC(CategoryCode.document, "Presentation Environment Control", new NotYetImplementedBuilder()),
    /** Page Modification Control */
    PMC(CategoryCode.page, "Page Modification Control", new NotYetImplementedBuilder());

    public static final TypeCode TYPE_CODE = TypeCode.Control;
    private final CategoryCode category;
    private final String name;
    private final Builder builder;

    private ControlType(CategoryCode category, String name, Builder builder) {
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