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
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * The End Resource structured field terminates an envelope that is used to carry resource objects
 * in external (print-file-level) resource groups. The envelope is initiated by a Begin Resource
 * (BRS) structured field.
 */
public class EndResource extends AbstractStructuredField {

    private final EndFieldName rsName;

    EndResource(StructuredFieldIntroducer introducer, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer);
        rsName = new EndFieldName(params);
    }

    /**
     * Returns the name of the resource being terminated. If a name is specified, it must match the
     * name in the most recent Begin Resource structured field. If the first two bytes in RSName
     * contain the value X'FFFF', the name matches any name specified on the Begin Resource
     * structured field that initiated the current definition.
     *
     * @return the resource name
     */
    public String getRSName() {
        return rsName.getName();
    }

    /**
     * Returns true if the page name should match any corresponding page name on the BeginResource
     * object.
     *
     * @return true if the page name should match any
     */
    public boolean nameMatchesAny() {
        return rsName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ResourceName", getRSName()));
        return params;
    }

    public static final class ERSBuilder implements Builder {
        @Override
        public EndResource build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException {
            return new EndResource(intro, params);
        }
    }
}
