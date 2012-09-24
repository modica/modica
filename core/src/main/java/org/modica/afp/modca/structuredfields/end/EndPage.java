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
 * The End Page structured field terminates the current presentation page definition initiated by a
 * Begin Page structured field.
 */
public class EndPage extends StructuredFieldWithTriplets {

    private final EndFieldName pageName;

    EndPage(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        pageName = new EndFieldName(params);
    }

    /**
     * Returns the name of the page that is being terminated. If a name is specified, it must match
     * the name in the most recent Begin Page structured field in the document or a X’01’ exception
     * condition exists. If the first two bytes of PageName contain the value X'FFFF', the name
     * matches any name specified on the Begin Page structuredfield that initiated the current
     * definition.
     *
     * @return the Page Name
     */
    public String getPageName() {
        return pageName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the BeginPage
     * object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return pageName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("PageName", getPageName()));
        return params;
    }

    public static final class EPGBuilder implements Builder {
        @Override
        public EndPage build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new EndPage(intro, TripletHandler.parseTriplet(params, 8, context), params);
        }
    }
}
