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

package org.modica.afp.modca.structuredfields.index;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.CPIRepeatingGroupLength;
import org.modica.afp.modca.common.GraphicalCharacterUseFlags;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * In a series of repeating groups, the Code Page Index (CPI) associates character IDs with code
 * points. Each repeating group specifies a character ID, a set of flags, and a code point. The
 * repeating groups may be sorted in ascending order by character IDs or by code point, depending on
 * the Sort Order flag in the Code Page Control structured field. The default sort order is by
 * ascending character ID order. One repeating group is required, but as many as 65,536 repeating
 * groups are allowed. The maximum number of repeating groups is determined by the maximum number of
 * code points in the code page: 256 for single-byte code pages, 256 for double-byte code page
 * sections, and 65,536 for double-byte code pages.
 * <p>
 * For processing efficiency, it is required that all code points in a single CPI correspond to the
 * same double-byte section. That is, each double-byte section shall begin a new CPI structured
 * field. For performance and storage efficiency, it is required that double-byte code pages be
 * sorted in ascending code point order. For migration consistency with the existing product base,
 * it is required that single-byte code pages be sorted in ascending character ID order (this
 * includes those code pages which were defined for use with double-byte raster fonts; each code
 * page object represented one double-byte code page section).
 *</p>
 */
public class CodePageIndex extends AbstractStructuredField {

    private final List<CPI> cpis;

    CodePageIndex(StructuredFieldIntroducer introducer, Parameters params, Context context)
            throws UnsupportedEncodingException {
        super(introducer);
        cpis = new ArrayList<CodePageIndex.CPI>();

        CPIRepeatingGroupLength rgLength = (CPIRepeatingGroupLength)
                context.get(ContextType.FOCA_CPI_REPEATING_GROUP_LENGTH);

        while (params.getPosition() < params.size() - 1) {
            cpis.add(new CPI(rgLength, params));
        }
    }

    /**
     * The data for the CPI structured field consists of a series of repeating group entries. Each
     * repeating group is divided as follows, with the length of the group specified by the CPI
     * Repeating Group Length parameter (byte 9) in the CPC structured field; the length is 10 bytes
     * for a single-byte code page and 11 bytes for a double-byte code page.
     */
    public static class CPI {

        private final String gcgid;
        private final boolean isInvalidCodedCharacter;
        private final boolean isNoPresentation;
        private final boolean isNoIncrement;
        private final int codePoint;
        private final long unicodeIndex;

        private CPI(CPIRepeatingGroupLength cpiRgLength, Parameters params)
                throws UnsupportedEncodingException {
            this.gcgid = params.getString(8);
            byte prntFlags = params.getByte();
            this.isInvalidCodedCharacter = GraphicalCharacterUseFlags.isInvalidCodedCharacter(prntFlags);
            this.isNoPresentation = GraphicalCharacterUseFlags.isNoPresentation(prntFlags);
            this.isNoIncrement = GraphicalCharacterUseFlags.isNoIncrement(prntFlags);
            if (cpiRgLength == CPIRepeatingGroupLength.DOUBLE_BYTE
                    || cpiRgLength == CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE) {
                this.codePoint = (int) params.getUInt(2);
            } else {
                this.codePoint = (int) params.getUInt(1);
            }
            if (cpiRgLength == CPIRepeatingGroupLength.SINGLE_BYTE_INC_UNICODE
                    || cpiRgLength == CPIRepeatingGroupLength.DOUBLE_BYTE_INC_UNICODE) {
                int unicodeLength = (int) params.getUInt(1);
                this.unicodeIndex = params.getUInt(unicodeLength);
            } else {
                unicodeIndex = 0;
            }
        }

        /**
         * The Graphic Character Global Identifier parameter specifies the registered identifier of
         * a graphic character. Unless otherwise specified, the default encoding is EBCDIC and the
         * default length is 8 bytes.
         *
         * @return the GCGID
         */
        public String getGcgid() {
            return gcgid;
        }

        /**
         * The Invalid Coded Character parameter specifies that the associated coded graphic
         * character is not valid and should not be used for processing. If this flag is off (0),
         * the coded graphic character is valid. If this flag is on (1), the coded graphic character
         * is not valid.
         *
         * @return true if this is an invalid character
         */
        public boolean isInvalidCodedCharacter() {
            return isInvalidCodedCharacter;
        }

        /**
         * The No Presentation parameter specifies that the corresponding coded character should be
         * ignored. If this flag is off (0), the coded character is presenting. If this flag is on
         * (1), the coded character is non-presenting.
         *
         * @return true if this character should not be presented
         */
        public boolean isNoPresentation() {
            return isNoPresentation;
        }

        /**
         * The No Increment parameter specifies that the character increment for the corresponding
         * coded character should be ignored. If this flag is off (0), the coded character is
         * incrementing. If this flag is on (1), the coded character is non-incrementing.
         *
         * @return true if the character should not be incremented
         */
        public boolean isNoIncrement() {
            return isNoIncrement;
        }

        /**
         * The Code Point parameter specifies the value of the integer sequence assigned to a
         * graphic character in an ordered list of control and graphic characters. The code point
         * numbers assigned depend on the code-page definition. The code page definition can be
         * specified by a user, or as specified in the appropriate product documentation.
         * <p>
         * The Code Point parameter consists of a one-byte binary number representing a graphic
         * character in a list of 256 potential control and graphic characters. If the Number of
         * Code Points Available parameter specifies that the available code points exceed 256, it
         * is necessary that this parameter is used with the Section Number parameter.
         *
         * @return the code point
         */
        public int getCodePoint() {
            return codePoint;
        }

        /**
         * To allow code pages that contain user-defined characters (that is, those  characters that
         * have not been registered with IBM and assigned a GCGID value) to be used with
         * TrueType/OpenType fonts, each code point can be mapped to one or more Unicode scalar
         * values. This function is selected by specifying either X'FE' (for a single-byte code
         * page) or X'FF' (for a double-byte code page) in the CPI repeating group length field
         * (CPC byte 9). In this case, each repeating group entry must contain a count value in
         * byte +0 that specifies the number of Unicode scalar values (bytes ++ 0–3) to follow. To
         * allow for combining characters, each code point in the code page can be mapped to a
         * different number of Unicode scalar values.
         *
         * @return the Unicode index
         */
        public long getUnicodeIndex() {
            return unicodeIndex;
        }

        /**
         * Returns the parameters of this code page index as a map of strings.
         *
         * @return the code page index as strings
         */
        public Map<String, String> getCPIStrings() {
            Map<String, String> strings = new HashMap<String, String>();
            strings.put("InvalidCodedCharacter", String.valueOf(isInvalidCodedCharacter));
            strings.put("NoPresentation", String.valueOf(isNoPresentation));
            strings.put("NoIncrement", String.valueOf(isNoIncrement));
            strings.put("CodePoint", String.valueOf(codePoint));
            strings.put("UnicodeIndex", String.valueOf(unicodeIndex));
            strings.put("GCGID", gcgid);
            return strings;
        }
    }

    /**
     * Returns an unmodifiable list of the code page indices that this object wraps.
     *
     * @return the code page indices
     */
    public List<CPI> getCodePageIndices() {
        return Collections.unmodifiableList(cpis);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }

    public static final class CPIBuilder implements Builder {
        @Override
        public CodePageIndex build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException {
            return new CodePageIndex(intro, params, context);
        }
    }
}
