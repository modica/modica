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
 * This fully qualified name object has an OID data type; The GID is an ASN.1 Object Identifier
 * (OID), defined in ISO/IEC 8824:1990(E). The data type is CODE. The OID is encoded using the
 * Basic Encoding Rules for ASN.1 specified in ISO/IEC 8825:1990(E).
 */
final class FQNOidData extends FullyQualifiedName {
    private final FQNType type;
    private final ObjectId oid;

    FQNOidData(int length, ObjectId oid, FQNType type) {
        super(length);
        this.type = type;
        this.oid = oid;
    }

    @Override
    public FQNType getFQNType() {
        return type;
    }

    @Override
    public FQNFmt getFormat() {
        return FQNFmt.oid;
    }

    /**
     * Get the Object Identifier.
     *
     * @return the object identifier
     */
    public ObjectId getOid() {
        return oid;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FQNOidData)) {
            return false;
        }
        FQNOidData obj = (FQNOidData) o;
        return this.type == obj.type
                && this.getLength() == obj.getLength()
                && this.oid.equals(obj.oid);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getLength();
        result = 31 * result + type.hashCode();
        result = 31 * result + oid.hashCode();
        return result;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString(getFQNType().name(), oid));
        return params;
    }
}
