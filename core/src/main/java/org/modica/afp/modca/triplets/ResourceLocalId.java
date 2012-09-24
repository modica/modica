/*
 * Licensed to the Apache Software Foimport java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.common.ByteUtils;
.
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
import org.modica.afp.modca.Parameters;
import org.modica.common.ByteUtils;

/**
 * The Resource Local Identifier triplet may be used to specify a resource type and a one-byte local
 * identifier or LID. The LID usually is associated with a specific resource name by a map
 * structured field, such as a Map Coded Font structured field.
 */
public class ResourceLocalId extends Triplet {

    private static final int LENGTH = 4;

    private final ResourceType resourceType;
    private final byte resourceLocalId;

    public ResourceLocalId(Parameters params) {
        resourceType = ResourceType.getValue(params.getByte());
        resourceLocalId = params.getByte();
    }

    /**
     * Specifies the resource type associated with the local ID.
     */
    public enum ResourceType {
        /**
         * Usage-dependent. The resource type is implied by the context of the structured field in
         * which this triplet parameter occurs.
         */
        USAGE_DEPENDENT(0x00),
        /** Page Overlay resource */
        PAGE_OVERLAY(0x02),
        /** Coded Font resource */
        CODED_FONT(0x05),
        /** Color Attribute Table resource */
        COLOR_ATTRIBUTE_TABLE(0x07);

        private byte value;

        private ResourceType(int value) {
            this.value = (byte) value;
        }

        private static ResourceType getValue(byte value) {
            for (ResourceType type : ResourceType.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Resource type value:" + value + " is invalid");
        }
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.resource_local_identifier;
    }

    /**
     * Returns the resource type associated with the local ID.
     *
     * @return the resource type
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Specifies a unique resource object local ID. It may be in the range of X'00' to X'FE'.
     *
     * @return returns the resource object local ID
     */
    public byte getResourceLocalId() {
        return resourceLocalId;
    }

    @Override
    public String toString() {
        return "ResourceLocalId type=" + resourceType.toString() + " id=" + resourceLocalId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResourceLocalId)) {
            return false;
        }
        ResourceLocalId obj = (ResourceLocalId) o;
        return this.resourceType == obj.resourceType
                && this.resourceLocalId == obj.resourceLocalId;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + resourceType.hashCode();
        result = 31 * result + resourceLocalId;
        result = 31 * result + getTid().hashCode();
        return result;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ResourceType", resourceType));
        params.add(new ParameterAsString("ResourceLocalId", ByteUtils.bytesToHex(resourceLocalId)));
        return params;
    }

}
