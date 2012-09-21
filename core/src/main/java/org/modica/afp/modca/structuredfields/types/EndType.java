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
import org.modica.afp.modca.structuredfields.end.EndActiveEnvironmentGroup.EAGBuilder;
import org.modica.afp.modca.structuredfields.end.EndCodePage.ECPBuilder;
import org.modica.afp.modca.structuredfields.end.EndDocument.EDTBuilder;
import org.modica.afp.modca.structuredfields.end.EndImageObject.EIMBuilder;
import org.modica.afp.modca.structuredfields.end.EndNamedPageGroup.ENGBuilder;
import org.modica.afp.modca.structuredfields.end.EndPage.EPGBuilder;
import org.modica.afp.modca.structuredfields.end.EndPresentationTextObject.EPTBuilder;
import org.modica.afp.modca.structuredfields.end.EndResourceGroup.ERGBuilder;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * An end structured field identifies the end of a document component. In general, an end
 * structured field may contain a parameter that identifies the name of the component.
 */
public enum EndType implements StructuredFieldType {
    /** End Page Segment */
    EPS(CategoryCode.page_segment, "Page Segment", new NotYetImplementedBuilder()),
    /** End Color Attribute Table */
    ECA(CategoryCode.color_attribute_table, "Color Attribute Table", new NotYetImplementedBuilder()),
    /** End IM Image (C) */
    EII(CategoryCode.im_image, "IM Image (C)", new NotYetImplementedBuilder()),
    /** End Code Page */
    ECP(CategoryCode.code_page, "Code Page", new ECPBuilder()),
    /** End Font */
    EFN(CategoryCode.font, "Font", new NotYetImplementedBuilder()),
    /** End Coded Font */
    ECF(CategoryCode.coded_font, "Coded Font", new NotYetImplementedBuilder()),
    /** End Object Container */
    EOC(CategoryCode.object_container, "Object Container", new NotYetImplementedBuilder()),
    /** End Presentation Text Object */
    EPT(CategoryCode.presentation_text, "Presentation Text Object", new EPTBuilder()),
    /** End Document Index */
    EDI(CategoryCode.index, "Document Index", new NotYetImplementedBuilder()),
    /** End Document */
    EDT(CategoryCode.document, "Document", new EDTBuilder()),
    /** End Named Page Group */
    ENG(CategoryCode.page_group, "Named Page Group", new ENGBuilder()),
    /** End Page */
    EPG(CategoryCode.page, "Page", new EPGBuilder()),
    /** End Graphics Object */
    EGR(CategoryCode.graphics, "Graphics Object", new NotYetImplementedBuilder()),
    /** End Document Environment Group */
    EDG(CategoryCode.document_environment_group, "Document Environment Group", new NotYetImplementedBuilder()),
    /** End Form Environment Group (O) */
    EFG(CategoryCode.form_environment_group, "Form Environment Group (O)", new NotYetImplementedBuilder()),
    /** End Resource Group */
    ERG(CategoryCode.resource_group, "Resource Group", new ERGBuilder()),
    /** End Object Environment Group */
    EOG(CategoryCode.object_environment_group, "Object Environment Group", new NotYetImplementedBuilder()),
    /** End Active Environment Group */
    EAG(CategoryCode.active_environment_group, "Active Environment Group", new EAGBuilder()),
    /** End Medium Map */
    EMM(CategoryCode.medium_map, "Medium Map", new NotYetImplementedBuilder()),
    /** End Form Map */
    EFM(CategoryCode.form_map, "Form Map", new NotYetImplementedBuilder()),
    /** End Resource */
    ERS(CategoryCode.name_resource, "Resource", new NotYetImplementedBuilder()),
    /** End Resource Environment Group */
    ESG(CategoryCode.resource_enviroment_group, "Resource Environment Group", new NotYetImplementedBuilder()),
    /** End Overlay */
    EMO(CategoryCode.overlay, "Overlay", new NotYetImplementedBuilder()),
    /** End Bar Code Object */
    EBC(CategoryCode.barcode, "Bar Code Object", new NotYetImplementedBuilder()),
    /** End Image Object */
    EIM(CategoryCode.image, "Image Object", new EIMBuilder());

    public static final TypeCode TYPE_CODE = TypeCode.End;
    private final CategoryCode category;
    private final String name;
    private final Builder builder;

    private EndType(CategoryCode category, String name, Builder builder) {
        this.category = category;
        this.name = "End " + name;
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
        handler.handleEnd(intro);
    }
}