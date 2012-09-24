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

package org.modica.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.ptoca.ControlSequence;
import org.modica.afp.ptoca.ControlSequenceParser;

/**
 * The Presentation Text Data structured field contains the data for a presentation text data
 * object.
 */
public class PresentationTextData extends AbstractStructuredField {

    private final List<ControlSequence> ptocaData;

    PresentationTextData(StructuredFieldIntroducer introducer, Parameters params,
            Context ctx) throws UnsupportedEncodingException {
        super(introducer);
        ptocaData = ControlSequenceParser.parse(params, ctx);
    }

    /**
     * Returns the PTOCA-defined control sequences that this object wraps.
     *
     * @return the PTOCA data
     */
    public List<ControlSequence> getPtocaData() {
        return Collections.unmodifiableList(ptocaData);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }

    public static final class PTXBuilder implements Builder {
        @Override
        public PresentationTextData build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException {
            return new PresentationTextData(intro, params, context);
        }
    }
}
