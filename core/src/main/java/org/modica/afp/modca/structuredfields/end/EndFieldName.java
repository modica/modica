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

package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;

import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.types.BeginType;
import org.modica.afp.modca.structuredfields.types.EndType;

/**
 * This object is to amalgamate the intelligence of a lot of the {@link EndType} type structured fields
 * that have names. The name can either match the name or it can match ANY name of a corresponding
 * {@link BeginType} structured field or it.
 */
class EndFieldName {

    private final String name;
    private final boolean nameMatchesAny;

    EndFieldName(Parameters params, int cpgid) throws UnsupportedEncodingException {
        if (params.size() < 2 ||
                (params.getByte() == (byte) 0xFF && params.getByte() == (byte) 0xFF)) {
            name = null;
            nameMatchesAny = true;
        } else {
            name = params.getStringAt(0, 8, cpgid);
            nameMatchesAny = false;
        }
    }

    EndFieldName(Parameters params) throws UnsupportedEncodingException {
        if (params.size() < 2 ||
                (params.getByte() == (byte) 0xFF && params.getByte() == (byte) 0xFF)) {
            name = null;
            nameMatchesAny = true;
        } else {
            name = params.getStringAt(0, 8);
            nameMatchesAny = false;
        }
    }

    String getName() {
        return name;
    }

    boolean matchesAny() {
        return nameMatchesAny;
    }

    @Override
    public String toString() {
        return nameMatchesAny ? "matches any" : name;
    }
}
