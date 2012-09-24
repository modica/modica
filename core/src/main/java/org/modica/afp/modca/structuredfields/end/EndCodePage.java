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
import org.modica.afp.modca.EbcdicStringHandler;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * The End Code Page (ECP) structured field ends the code page object.
 */
public class EndCodePage extends AbstractStructuredField {

    private final EndFieldName cpName;

    EndCodePage(StructuredFieldIntroducer introducer, Parameters params, Context ctx)
            throws UnsupportedEncodingException {
        super(introducer);
        cpName = new EndFieldName(params, EbcdicStringHandler.DEFAULT_CPGID);
        ctx.endCodePage();
    }

    /**
     * The ECP structured field name, if supplied, must match the corresponding BCP structured field
     * name. In an ECP structured field, no name or a null name (any name with X'FFFF' in the first
     * two bytes) matches any name in the BCP structured field.
     *
     * @return the code page name
     */
    public String getCodePageName() {
        return cpName.getName();
    }

    /**
     * If the name begins with X'FFFF' this matches any name on the BeginCodePage field.
     *
     * @return if the name matches any
     */
    public boolean nameMatchesAny() {
        return cpName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("CodePageName", getCodePageName()));
        return params;
    }

    public static final class ECPBuilder implements Builder {
        @Override
        public EndCodePage build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException {
            return new EndCodePage(intro, params, context);
        }
    }
}
