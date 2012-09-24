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
import java.util.Arrays;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;

/**
 * In the case of FQN type X'BE'—Other Object Internal Resource Reference, the data type of the GID
 * reference is undefined (UNDF) at the MO:DCA data stream level; it is not character (CHAR) data.
 * In that case the data type is defined internally by the data object that generates the reference.
 */
public class FQNUndefData extends FullyQualifiedName {
    private final byte[] undefData;
    private final FQNType type;

    FQNUndefData(int length, byte[] undefData, FQNType type) {
        super(length);
        this.undefData = undefData;
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
     * Get the undefined data array.
     *
     * @return the undefined data
     */
    public byte[] getUndefData() {
        byte[] ret = new byte[undefData.length];
        System.arraycopy(undefData, 0, ret, 0, undefData.length);
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FQNUndefData)) {
            return false;
        }
        FQNUndefData obj = (FQNUndefData) o;
        return getLength() == obj.getLength()
                && Arrays.equals(this.undefData, obj.undefData)
                && this.type == obj.type;
    }

    @Override
    public int hashCode() {
        int ret = 17;
        for (byte b : undefData) {
            ret = 31 * ret + b;
        }
        ret = 31 * ret + type.hashCode();
        return ret;

    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("Undefined Data", ""));
        return params;
    }

}
