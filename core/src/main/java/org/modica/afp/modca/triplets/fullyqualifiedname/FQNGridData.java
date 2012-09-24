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
 * A Fully Qualified Name that contains a Global Resource Identification.
 */
public class FQNGridData extends FullyQualifiedName {
    private final FQNType type;
    private final GlobalResourceId grid;

    FQNGridData(int length, GlobalResourceId grid, FQNType type) {
        super(length);
        this.grid = grid;
        this.type = type;
    }

    @Override
    public FQNType getFQNType() {
        return type;
    }

    @Override
    public FQNFmt getFormat() {
        return FQNFmt.character_string;
    }

    /**
     * Returns the Global Resource Identification data.
     *
     * @return the grid
     */
    public GlobalResourceId getGrid() {
        return grid;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FQNGridData)) {
            return false;
        }
        FQNGridData obj = (FQNGridData) o;
        return this.getLength() == obj.getLength()
                && this.grid.equals(obj.grid)
                && this.type == obj.type;
    }

    @Override
    public int hashCode() {
        int ret = 17;
        ret = 31 * ret + getLength();
        ret = 31 * ret + grid.hashCode();
        ret = 31 * ret + type.hashCode();
        return ret;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("GlobalResourceId", grid));
        return params;
    }

}
