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
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * A copy count structured field specifies groups of sheet copies, called copy subgroups, that are
 * to be generated, and identifies modification control structured fields that specify modifications
 * to be applied to each group.
 */
public enum CopyCountType implements StructuredFieldType {
    /** Medium Copy Count */
    MCC(CategoryCode.medium, "Medium Copy Count", new NotYetImplementedBuilder()),
    /** Font Patterns Map */
    FNM(CategoryCode.font, "Font Patterns Map", new NotYetImplementedBuilder());

    public static final TypeCode TYPE_CODE = TypeCode.CopyCount;
    private final CategoryCode category;
    private final String name;
    private final Builder builder;

    private CopyCountType(CategoryCode categoryCode, String name, Builder builder) {
        this.category = categoryCode;
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