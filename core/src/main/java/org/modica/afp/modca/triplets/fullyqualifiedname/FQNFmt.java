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

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.modica.afp.modca.Parameters;

/**
 * Specified the GID format.
 */
public enum FQNFmt {
    character_string(0x00) {
        @Override
        FullyQualifiedName createFQN(FQNType type, Parameters params, int length, int dataLength)
                throws UnsupportedEncodingException {
            return FullyQualifiedName.handleStringData(type, params, length, dataLength);
        }
    },
    oid(0x10) {
        @Override
        FullyQualifiedName createFQN(FQNType type, Parameters params, int length, int dataLength) {
            ObjectId oid = new ObjectId(params, dataLength);
            return new FQNOidData(length, oid, type);
        }
    },
    url(0x20) {
        @Override
        FullyQualifiedName createFQN(FQNType type, Parameters params, int length, int dataLength)
                throws MalformedURLException, UnsupportedEncodingException {
            String url = params.getString(dataLength);
            return new FQNUrlData(length, new URL(url), type);
        }
    };

    private final byte id;

    private FQNFmt(int id) {
        this.id = (byte) id;
    }

    /**
     * Return the identity byte of this FQN format.
     *
     * @return the byte identifying the FQN format
     */
    byte getId() {
        return id;
    }

    abstract FullyQualifiedName createFQN(FQNType type, Parameters params, int length,
            int dataLength) throws UnsupportedEncodingException, MalformedURLException;

    public static FQNFmt getValue(byte id) {
        for (FQNFmt format : FQNFmt.values()) {
            if (format.id == id) {
                return format;
            }
        }
        throw new IllegalArgumentException("Invalid Fully Qualified Name format");
    }
}