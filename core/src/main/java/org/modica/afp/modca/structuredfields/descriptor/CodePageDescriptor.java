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

package org.modica.afp.modca.structuredfields.descriptor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.EncodingScheme;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/** The Code Page Descriptor (CPD) structured field describes the code page. */
public class CodePageDescriptor extends AbstractStructuredField {

    private final String cpDesc;
    private static final int GCGIDLEN = 8;
    private final long numCdPts;
    private final int gcsgid;
    private final int cpgid;
    private final EncodingScheme encScheme;

    CodePageDescriptor(StructuredFieldIntroducer introducer, Parameters params, Context ctx)
            throws UnsupportedEncodingException {
        super(introducer);
        cpDesc = params.getString(32);
        int gcgidLength = (int) params.getUInt(2);
        assert gcgidLength == GCGIDLEN;
        numCdPts = params.getUInt(4);
        gcsgid = (int) params.getUInt(2);
        cpgid = (int) params.getUInt(2);
        if (params.bytesRemaining() > 0) {
            encScheme = EncodingScheme.getValue(params.getByte());
            params.skip(1); // The encoding scheme only uses the first of 2 bytes
        } else {
            encScheme = null;
        }
        ctx.setCpgidForCodePage(this);
    }

    /**
     * The character string assigned to this field is intended to aid the end user, who may need to
     * edit the code page, in identifying the set of characters represented by the code page. The
     * name or title used should correspond to the name assigned by the IBM registration authority
     * when the code page was registered. Though it may be any name or title that has meaning to
     * the creator or editor of the code page.
     *
     * @return code page descriptor
     */
    public String getCodePageDescriptor() {
        return cpDesc;
    }

    /**
     * The Number of Coded Graphic Characters Assigned parameter specifies the number of graphic
     * characters (code points in one or more sections) that have been assigned in a code page.
     *
     * @return the number of coded graphic characters
     */
    public long getNumCdPts() {
        return numCdPts;
    }

    /**
     * Returns the Graphic Character Set Global Identifier (GCSGID) parameter specifies the number
     * assigned to a graphic character set, which identifies the set of graphic characters contained
     * in the font resource. The Graphic Character Set Global ID numbers that are supported, are
     * specified in IBM product documentation.
     *
     * @return the GCSGID
     */
    public int getGcsgid() {
        return gcsgid;
    }

    /**
     * Returns the Code Page Global Identifier (CPGID) parameter specifies the number assigned to a
     * code page. The code page numbers that are supported are specified in IBM product
     * documentation.
     *
     * @return the CPGID
     */
    public int getCpgid() {
        return cpgid;
    }

    /**
     * Returns the Encoding Scheme.
     *
     * @return the encoding scheme
     */
    public EncodingScheme getEncodingScheme() {
        return encScheme;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("Description", cpDesc));
        params.add(new ParameterAsString("NumberOfCodePoints", numCdPts));
        params.add(new ParameterAsString("GCSGID", gcsgid));
        params.add(new ParameterAsString("CPGID", cpgid));
        params.add(new ParameterAsString("EncodingScheme", encScheme));
        return params;
    }

    public static final class CPDBuilder implements Builder {
        @Override
        public CodePageDescriptor build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new CodePageDescriptor(intro, params, context);
        }
    }
}
