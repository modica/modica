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

package org.modica.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Begin Document structured field names and begins the document.
 */
public final class BeginDocument extends StructuredFieldWithTriplets {

    private final String documentName;
    private final boolean docNameProvidedBySystem;

    BeginDocument(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        if (params.getByte() != (byte) 0xFF && params.getByte() != (byte) 0xFF) {
            documentName = params.getStringAt(0, 8);
            docNameProvidedBySystem = false;
        } else {
            docNameProvidedBySystem = true;
            documentName = null;
        }
    }

    /**
     * The document name is the name of the document described by the data stream. If a Fully
     * Qualified Name type X’01’ (Replace First GID) appears in this structured field, the name
     * specified in this parameter is ignored and the GID provided by the triplet is used instead.
     * If the value of the first two bytes of DocName are X'FFFF', the processing system provides
     * the document name.
     * <p>
     * Whether or not the DocName is provided by the system can be ascertained from the
     * {@code docNameProvidedBySystem()} method.</p>
     *
     * @return the document name
     */
    public String getDocName() {
        return documentName;
    }

    /**
     * Returns true if the document name is provided by the system.
     *
     * @return true if the document name is provided by the system
     */
    public boolean docNameProvidedBySystem() {
        return docNameProvidedBySystem;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("DocumentName", documentName));
        return params;
    }

    public static final class BDTBuilder implements Builder {
        @Override
        public BeginDocument build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new BeginDocument(intro, TripletHandler.parseTriplet(params, 10, context), params);
        }
    }
}
