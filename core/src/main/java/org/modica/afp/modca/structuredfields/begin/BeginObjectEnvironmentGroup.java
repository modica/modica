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
 * The Begin Object Environment Group structured field begins an Object Environment Group, which
 * establishes the environment parameters for the object. The scope of an object environment group
 * is its containing object.
 */
public class BeginObjectEnvironmentGroup extends StructuredFieldWithTriplets {

    private final String oegName;

    BeginObjectEnvironmentGroup(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params) throws UnsupportedEncodingException {
        super(introducer, triplets);
        oegName = params.getStringAt(0, 8);
    }

    /**
     * Returns the name of the object environment group.
     *
     * @return the OEG name
     */
    public String getOegName() {
        return oegName;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ObjectEnvironmentGroupName", oegName));
        return params;
    }

    public static final class BOGBuilder implements Builder {
        @Override
        public BeginObjectEnvironmentGroup build(StructuredFieldIntroducer intro,
                Parameters params, Context context) throws UnsupportedEncodingException,
                MalformedURLException {
            return new BeginObjectEnvironmentGroup(intro, TripletHandler.parseTriplet(params, 8,
                    context), params);
        }
    }
}
