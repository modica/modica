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
 * The End Active Environment Group structured field terminates the definition of an Active
 * Environment Group initiated by a Begin Active Environment Group structured field.
 */
public class EndActiveEnvironmentGroup extends AbstractStructuredField {

    private final EndFieldName aegName;

    EndActiveEnvironmentGroup(StructuredFieldIntroducer introducer, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer);
        aegName = new EndFieldName(params);
    }

    /**
     * Returns the name of the active environment group being terminated. If a name is specified, it
     * must match the name in the most recent Begin Active Environment Group structured field in the
     * page or a X’01’ exception condition exists. If the first two bytes in AEGName contain the
     * value X'FFFF', the name matches any name specified on the Begin Active Environment Group
     * structured field that initiated the current definition.
     *
     * @return the AEG name
     */
    public String getAegName() {
        return aegName.getName();
    }

    /**
     * Returs if the first two bytes in AEGName contain the value X'FFFF', the name matches any name
     * specified on the Begin Active Environment Group structured field that initiated the current
     * definition.
     *
     * @return whether or not the AEG name matches any in the corresponding
     * BeginActiveEnvironmentGroup
     */
    public boolean nameMatchesAny() {
        return aegName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ActiveEnvironmentGroupName", getAegName()));
        return params;
    }

    public static final class EAGBuilder implements Builder {
        @Override
        public EndActiveEnvironmentGroup build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException {
            return new EndActiveEnvironmentGroup(intro, params);
        }
    }
}
