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
import org.modica.afp.modca.structuredfields.include.IncludeObject.IOBBuilder;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * An include structured field selects a named resource which is to be embedded in the including
 * data stream as if it appeared inline. External resource object names on the begin structured
 * field may or may not coincide with the library name of that object, as library name resolution is
 * outside the scope of the MO:DCA architecture.
 */
public enum IncludeType implements StructuredFieldType {
    /** Include Page Segment */
    IPS(CategoryCode.page_segment, "Page Segment", new NotYetImplementedBuilder()),
    /** Include Page */
    IPG(CategoryCode.page, "Page", new NotYetImplementedBuilder()),
    /** Include Object */
    IOB(CategoryCode.data_resource, "Object", new IOBBuilder()),
    /** Include Page Overlay */
    IPO(CategoryCode.page_overlay, "Page Overlay", new NotYetImplementedBuilder());

    private static final TypeCode TYPE_CODE = TypeCode.Include;
    private final CategoryCode category;
    private final String name;
    private final Builder builder;

    private IncludeType(CategoryCode category, String name, Builder builder) {
        this.category = category;
        this.name = "Include " + name;
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