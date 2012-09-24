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
import org.modica.common.ByteUtils;

/**
 * The Descriptor Position triplet is used to associate an Object Area Position structured field
 * with an Object Area Descriptor structured field.
 */
public class DescriptorPosition extends Triplet {

    private final static int LENGTH = 3;
    private final byte desPosId;

    public DescriptorPosition(byte desPosId) {
        this.desPosId = desPosId;
    }

    /**
     * Specifies the identifier of the Object Area Position structured field that is associated with
     * the descriptor for this object area.
     *
     * @return get the identifier
     */
    public byte getDesPosId() {
        return desPosId;
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.descriptor_position;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DescriptorPosition)) {
            return false;
        }
        DescriptorPosition obj = (DescriptorPosition) o;
        return this.desPosId == obj.desPosId;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + desPosId;
        hashCode = 31 * hashCode + getTid().hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return getTid().toString() + " DesPosID=" + desPosId;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ObjectAreaPositionId", ByteUtils.bytesToHex(desPosId)));
        return params;
    }
}
