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
import org.modica.afp.modca.EbcdicStringHandler;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Begin Font (BFN) structured field begins the font character set object.
 */
public class BeginFont extends StructuredFieldWithTriplets {

    private final String csName;

    BeginFont(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params) throws UnsupportedEncodingException {
        super(introducer, triplets);
        csName = params.getStringAt(0, 8, EbcdicStringHandler.DEFAULT_CPGID);
    }

    /**
     * Returns the Font Character Set Name.
     * <p>
     * If used, it is suggested that the font character set name be the same name used to reference
     * the object. See bytes 0–7 of the Coded Font Index (CFI) repeating group in this document, or
     * the Map Coded Font (MCF) of the Mixed Object Document Content Architecture Reference,
     * SC31-6802.
     * </p>
     *
     * @return the font character set name
     */
    public String getCharacterSetName() {
        return csName;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("CharactersetName", csName));
        return params;
    }

    public static final class BFNBuilder implements Builder {
        @Override
        public BeginFont build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new BeginFont(intro, TripletHandler.parseTriplet(params, 8, context), params);
        }
    }
}
