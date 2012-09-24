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

import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletIdentifiers;

/**
 * An abstract class that all Fully Qualified Name triplets inherit from.
 */
public abstract class FullyQualifiedName extends Triplet {
    private final int length;
    private static final TripletIdentifiers tId = TripletIdentifiers.fully_qualified_name;

    FullyQualifiedName(int length) {
        this.length = length;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public TripletIdentifiers getTid() {
        return tId;
    }

    /**
     * Returns the type of Fully Qualified Name triplet this object represents.
     *
     * @return the fully qualified name type
     */
    public abstract FQNType getFQNType();

    /**
     * Each Fully Qualified Name object is one of three types enumerated in {@link FQNFmt}, this
     * returns the type of this FQN.
     *
     * @return the FQN format type
     */
    public abstract FQNFmt getFormat();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    //TODO: This may need to be pulled out into it's own class i.e. FQNHandler
    public static FullyQualifiedName parse(Parameters params, int length)
            throws UnsupportedEncodingException, MalformedURLException {
        FQNType type = FQNType.getValue(params.getByte());
        FQNFmt format = FQNFmt.getValue(params.getByte());
        assert type != null;
        // the length field is included in the length of the triplet
        int dataLength = length - 4;
        return format.createFQN(type, params, length, dataLength);
    }

    static FullyQualifiedName handleStringData(FQNType type, Parameters params,
            int fqnLength, int stringLength) throws UnsupportedEncodingException {
        switch (type) {
        case begin_resource_object_ref:
            GlobalResourceId grid = new GlobalResourceId(params);
            return new FQNGridData(fqnLength, grid, type);
        case data_object_internal_resource_ref:
            int undefLength = params.size() - params.getPosition();
            byte[] undefData = params.getByteArray(undefLength);
            return new FQNUndefData(stringLength, undefData, type);
        default:
            return new FQNCharStringData(fqnLength, params.getString(stringLength), type);
        }
    }
}
