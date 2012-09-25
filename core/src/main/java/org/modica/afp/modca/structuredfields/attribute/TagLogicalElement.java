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

package org.modica.afp.modca.structuredfields.attribute;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * A Tag Logical Element structured field assigns an attribute name and an attribute value to a page
 * or page group. The Tag Logical Element structured field may be embedded directly in the page or
 * page group, or it may reference the page or page group from a document index. When a Tag Logical
 * Element structured field references a page or is embedded in a page following the active
 * environment group, it is associated with the page. When a Tag Logical Element structured field
 * references a page group or is embedded in a page group following the Begin Named Page Group
 * structured field, it is associated with the page group. When a Tag Logical Element structured
 * field is associated with a page group, the parameters of the Tag Logical Element structured field
 * are inherited by all pages in the page group and by all other page groups that are nested in the
 * page group. The scope of a Tag Logical Element is determined by its position with respect to
 * other TLEs that reference, or are embedded in, the same page or page group. The Tag Logical
 * Element structured field does not provide any presentation specifications and therefore has no
 * effect on the appearance of a document when it is presented.
 */
public class TagLogicalElement extends StructuredFieldWithTriplets {

    public TagLogicalElement(StructuredFieldIntroducer introducer, List<Triplet> triplets) {
        super(introducer, triplets);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        return Collections.emptyList();
    }

    public static class TLEBuilder implements Builder {
        @Override
        public TagLogicalElement build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new TagLogicalElement(intro, TripletHandler.parseTriplet(params, 0, context));
        }
    }
}
