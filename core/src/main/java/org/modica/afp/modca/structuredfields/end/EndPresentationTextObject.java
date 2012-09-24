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
 * The End Presentation Text Object structured field terminates the current presentation text object
 * initiated by a Begin Presentation Text Object structured field.
 */
public class EndPresentationTextObject extends StructuredFieldWithTriplets {

    private final EndFieldName pTdoName;

    EndPresentationTextObject(StructuredFieldIntroducer introducer, List<Triplet> triplets,
            Parameters params) throws UnsupportedEncodingException {
        super(introducer, triplets);
        pTdoName = new EndFieldName(params);
    }

    /**
     * Returns the name of the presentation text data object that is being terminated. If a name is
     * specified, it must match the name in the most recent Begin Presentation Text Object
     * structured field in the page, or overlay, or a X’01’ exception condition exists. If the first
     * two bytes of PTdoName contain the value X'FFFF', the name matches any name specified on the
     * Begin Presentation Text Object structured field that initiated the current definition.
     *
     * @return the Presentation Text Object that this object ends
     */
    public String getPTdoName() {
        return pTdoName.getName();
    }

    /**
     * Whether or not the name of this object matches any name in the BeginPresentationTextObject.
     *
     * @return true if the name matches any
     */
    public boolean nameMatchesAny() {
        return pTdoName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("PresentationTextObjectName", getPTdoName()));
        return params;
    }

    public static final class EPTBuilder implements Builder {
        @Override
        public EndPresentationTextObject build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new EndPresentationTextObject(intro, TripletHandler.parseTriplet(params, 8,
                    context), params);
        }
    }
}
