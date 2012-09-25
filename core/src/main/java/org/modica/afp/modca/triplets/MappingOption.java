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

package org.modica.afp.modca.triplets;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.common.MapValue;

/**
 * The Mapping Option is used to specify the mapping of a data object presentation space to an
 * object area.
 */
public class MappingOption extends Triplet {
    private static final int LENGTH = 3;
    private final MapValue mapValue;

    public MappingOption(byte mapValue) {
        this.mapValue = MapValue.getMapValue(mapValue);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    /**
     * Get the map value.
     *
     * @return the map value
     */
    public MapValue getMapvalue() {
        return mapValue;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.mapping_option;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MappingOption)) {
            return false;
        }
        MappingOption obj = (MappingOption) o;
        return this.mapValue == obj.mapValue;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + mapValue.hashCode();
        hashCode = 31 * hashCode + getTid().hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return getTid().name() + " mapValue=" + mapValue;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("MapValue", mapValue));
        return params;
    }
}
