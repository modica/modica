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

/** A class that handles all the methods common to all structured fields. */
public abstract class AbstractStructuredField implements StructuredField {

    private final StructuredFieldIntroducer introducer;

    public AbstractStructuredField(StructuredFieldIntroducer introducer) {
        this.introducer = introducer;
    }

    @Override
    public boolean hasExtData() {
        return introducer.hasExtData();
    }

    @Override
    public boolean hasSegmentedData() {
        return introducer.hasSegmentedData();
    }

    @Override
    public boolean hasDataPadding() {
        return introducer.hasDataPadding();
    }

    @Override
    public int getLength() {
        return introducer.getLength();
    }

    @Override
    public StructuredFieldType getType() {
        return introducer.getType();
    }

    @Override
    public long getOffset() {
        return introducer.getOffset();
    }

    @Override
    public int getExtLength() {
        return introducer.getExtLength();
    }

    @Override
    public int bytesToNextStructuredField() {
        return introducer.bytesToNextStructuredField();
    }

    @Override
    public String toString() {
        return getType().getName();
    }
}
