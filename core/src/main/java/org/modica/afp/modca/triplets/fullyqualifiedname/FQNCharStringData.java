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

package org.modica.afp.modca.triplets.fullyqualifiedname;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;

/**
 * Provides functionality for the Fully Qualified Name triplets that have character string data.
 */
public final class FQNCharStringData extends FullyQualifiedName {
    private final String data;
    private final FQNType type;

    FQNCharStringData(int length, String data, FQNType type) {
        super(length);
        this.data = data;
        this.type = type;
    }

    /**
     * Returns the character string data.
     *
     * @return the character string
     */
    public String getString() {
        return this.data;
    }

    @Override
    public FQNFmt getFormat() {
        return FQNFmt.character_string;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FQNCharStringData)) {
            return false;
        }
        FQNCharStringData fqn = (FQNCharStringData) o;
        return this.data.equals(fqn.data)
                && this.getFQNType() == fqn.getFQNType()
                && this.getLength() == fqn.getLength();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getLength();
        result = 31 * result + getFQNType().hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getTid().name() + ", " + getFQNType() + "=" + data;
    }

    @Override
    public FQNType getFQNType() {
        return type;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString(getFQNType().name(), data));
        return params;
    }
}
