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

import java.util.Arrays;

import org.modica.afp.modca.Parameters;

/**
 * The GID is an ASN.1 Object Identifier (OID), defined in ISO/IEC 8824:1990(E). The data type
 * is CODE. The OID is encoded using the Basic Encoding Rules for ASN.1 specified in ISO/IEC
 * 8825:1990(E).
 */
public final class ObjectId {
    private final byte[] oid;
    public static final int OID_ENCODING = 0x06;

    ObjectId(Parameters params, int length) {
        // The first bit of the length is always set to 0
        byte oidEncoding = params.getByte();
        assert oidEncoding == OID_ENCODING;
        int pos = params.getPosition();
        oid = params.getByteArray(pos, params.size() - pos);
    }

    /**
     * Returns the Object Identification data.
     *
     * @return the oid
     */
    byte[] getOid() {
        byte[] ret = new byte[oid.length];
        System.arraycopy(oid, 0, ret, 0, oid.length);
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ObjectId)) {
            return false;
        }
        ObjectId obj = (ObjectId) o;
        return Arrays.equals(this.oid, obj.oid);
    }

    @Override
    public int hashCode() {
        int ret = 17;
        for (byte b : oid) {
            ret = 31 * ret + b;
        }
        return ret;
    }
}
