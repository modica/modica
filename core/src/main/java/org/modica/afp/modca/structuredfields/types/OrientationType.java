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

/** */
public enum OrientationType implements StructuredFieldType {
    /** Font Orientation */
    FNO(CategoryCode.font, "Font Orientation", new NotYetImplementedBuilder());

    private static final TypeCode TYPE_CODE = TypeCode.Orientation;
    private final String name;
    private final CategoryCode category;
    private final Builder builder;

    private OrientationType(CategoryCode category, String name, Builder builder) {
        this.name = name;
        this.category = category;
        this.builder = builder;
    }

    @Override
    public CategoryCode getCategoryCode() {
        return category;
    }

    @Override
    public TypeCode getTypeCode() {
        return TYPE_CODE;
    }

    @Override
    public String getName() {
        return name;
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