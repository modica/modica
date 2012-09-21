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
import org.modica.afp.modca.structuredfields.begin.BeginActiveEnvironmentGroup.BAGBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginCodePage.BCPBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginDocument.BDTBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginFont.BFNBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginImageObject.BIMBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginNamedPageGroup.BNGBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginObjectEnvironmentGroup.BOGBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginPage.BPGBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginPresentationTextObject.BPTBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginResource.BRSBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginResourceGroup.BRGBuilder;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * A begin structured field introduces and identifies a document component. In general, a begin
 * structured field may contain a parameter that identifies the name of the component.
 */
public enum BeginType implements StructuredFieldType {
    /** Page Segment */
    BPS(CategoryCode.page_segment, "Page Segment", new NotYetImplementedBuilder()),
    /** Color Attribute Table */
    BCA(CategoryCode.color_attribute_table, "Color Attribute Table", new NotYetImplementedBuilder()),
    /** IM Image (C) */
    BII(CategoryCode.im_image, "IM Image (C)", new NotYetImplementedBuilder()),
    /** Code Page */
    BCP(CategoryCode.code_page, "Code Page", new BCPBuilder()),
    /** Font */
    BFN(CategoryCode.font, "Font", new BFNBuilder()),
    /** Coded Font */
    BCF(CategoryCode.coded_font, "Coded Font", new NotYetImplementedBuilder()),
    /** Object Container */
    BOC(CategoryCode.object_container, "Object Container", new NotYetImplementedBuilder()),
    /** Presentation Text Object */
    BPT(CategoryCode.presentation_text, "Presentation Text Object", new BPTBuilder()),
    /** Document Index */
    BDI(CategoryCode.index, "Document Index", new NotYetImplementedBuilder()),
    /** Document */
    BDT(CategoryCode.document, "Document", new BDTBuilder()),
    /** Named Page Group */
    BNG(CategoryCode.page_group, "Named Page Group", new BNGBuilder()),
    /** Page */
    BPG(CategoryCode.page, "Page", new BPGBuilder()),
    /** Graphics Object */
    BGR(CategoryCode.graphics, "Graphics Object", new NotYetImplementedBuilder()),
    /** Document Environment Group */
    BDG(CategoryCode.document_environment_group, "Document Environment Group", new NotYetImplementedBuilder()),
    /** Form Environment Group (O) */
    BFG(CategoryCode.form_environment_group, "Form Environment Group (O)", new NotYetImplementedBuilder()),
    /** Resource Group */
    BRG(CategoryCode.resource_group, "Resource Group", new BRGBuilder()),
    /** Object Environment Group */
    BOG(CategoryCode.object_environment_group, "Object Environment Group", new BOGBuilder()),
    /** Active Environment Group */
    BAG(CategoryCode.active_environment_group, "Active Environment Group", new BAGBuilder()),
    /** Medium Map */
    BMM(CategoryCode.medium_map, "Medium Map", new NotYetImplementedBuilder()),
    /** Form Map */
    BFM(CategoryCode.form_map, "Form Map", new NotYetImplementedBuilder()),
    /** Resource */
    BRS(CategoryCode.name_resource, "Resource", new BRSBuilder()),
    /** Resource Environment Group */
    BSG(CategoryCode.resource_enviroment_group, "Resource Environment Group", new NotYetImplementedBuilder()),
    /** Overlay */
    BMO(CategoryCode.overlay, "Overlay", new NotYetImplementedBuilder()),
    /** Bar Code Object */
    BBC(CategoryCode.barcode, "Bar Code Object", new NotYetImplementedBuilder()),
    /** Image Object */
    BIM(CategoryCode.image, "Image Object", new BIMBuilder());

    public static final TypeCode TYPE_CODE = TypeCode.Begin;
    private final CategoryCode category;
    private final String name;
    private final Builder builder;

    private BeginType(CategoryCode category, String name, Builder builder) {
        this.category = category;
        this.name = "Begin " + name;
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
        handler.handleBegin(intro);
    }
}