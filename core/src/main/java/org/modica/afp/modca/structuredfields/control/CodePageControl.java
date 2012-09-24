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

package org.modica.afp.modca.structuredfields.control;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.CPIRepeatingGroupLength;
import org.modica.afp.modca.common.GraphicalCharacterUseFlags;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * The Code Page Control (CPC) contains information about the code page.
 */
public class CodePageControl extends AbstractStructuredField {

    private final String defCharId;
    private final byte printFlags;
    private final boolean isInvalidCodedCharacter;
    private final boolean isNoPresentation;
    private final boolean isNoIncrement;
    private final CPIRepeatingGroupLength cpRgLen;
    private final int vsCharSn;
    private final int vsChar;
    private final boolean isAscendingCodePoint;
    private final boolean isVariableSpaceEnabled;
    private final long defaultUnicodeValue;

    CodePageControl(StructuredFieldIntroducer introducer, Parameters params, Context context)
            throws UnsupportedEncodingException {
        super(introducer);
        defCharId = params.getString(8);
        printFlags = params.getByte();
        isInvalidCodedCharacter = GraphicalCharacterUseFlags.isInvalidCodedCharacter(printFlags);
        isNoPresentation = GraphicalCharacterUseFlags.isNoPresentation(printFlags);
        isNoIncrement = GraphicalCharacterUseFlags.isNoIncrement(printFlags);
        cpRgLen = CPIRepeatingGroupLength.getValue(params.getByte());
        vsCharSn = (int) params.getUInt(1);
        vsChar = (int) params.getUInt(1);
        byte vsFlags = params.getByte();
        isAscendingCodePoint = CodePageUseFlags.isAscendingCodePoint(vsFlags);
        isVariableSpaceEnabled = CodePageUseFlags.isVariableSpaceEnabled(vsFlags);
        if (cpRgLen.isUnicode()) {
            defaultUnicodeValue = params.getUInt(params.size() - params.getPosition());
        } else {
            defaultUnicodeValue = 0;
        }

        context.put(ContextType.FOCA_CPI_REPEATING_GROUP_LENGTH, cpRgLen);
    }

    private enum CodePageUseFlags {
        SORT_ORDER(0),
        VARIABLE_SPACE_ENABLE(4);

        private final byte bitMask;

        private CodePageUseFlags(int bitNumber) {
            bitMask = (byte) (1 << 7 - bitNumber);
        }

        private static boolean isAscendingCodePoint(byte flag) {
            return (flag & SORT_ORDER.bitMask) != 0;
        }

        private static boolean isVariableSpaceEnabled(byte flag) {
            return (flag & VARIABLE_SPACE_ENABLE.bitMask) != 0;
        }
    }

    /**
     * The character ID of the character used when no character is assigned to a code point
     * The default character must be named in bytes 0–7 of a repeating group in the Font Index
     * FNI) structured field.
     *
     * @return the default GCGID
     */
    public String getDefaultGCGID() {
        return defCharId;
    }

    /**
     * The Invalid Coded Character parameter specifies that the associated coded graphic character
     * is not valid and should not be used for processing. If this flag is off (0), the coded
     * graphic character is valid. If this flag is on (1), the coded graphic character is not valid.
     *
     * @return if the invalid coded character flag is set
     */
    public boolean isInvalidCodedCharacter() {
        return isInvalidCodedCharacter;
    }

    /**
     * The No Presentation parameter specifies that the corresponding coded character should be
     * ignored. If this flag is off (0), the coded character is presenting. If this flag is on (1),
     * the coded character is non-presenting.
     *
     * @return true if the no presentation flag is set to on
     */
    public boolean isNoPresensentation() {
        return isNoPresentation;
    }

    /**
     * The No Increment parameter specifies that the character increment for the corresponding coded
     * character should be ignored. If this flag is off (0), the coded character is incrementing. If
     * this flag is on (1), the coded character is non-incrementing.
     *
     * @return true if the no increment flag is set to on
     */
    public boolean isNoIncrement() {
        return isNoIncrement;
    }

    /**
     * The length of the CPI repeating group is dependent on the length of the code point as defined
     * by the Encoding Scheme parameter in the CPD Structured Field. A single-byte code point would
     * result in a CPI length of X'0A', while a double-byte code point would result in a length of
     * X'0B'.
     * <p>
     * When GCGID-to-Unicode mappings are provided (values X'FE' or X'FF'), the length of each CPI
     * repeating group can vary depending on how many Unicode scalar values are associated with each
     * code point.
     *
     * @return the length of the CPI repeating group
     */
    public CPIRepeatingGroupLength getCPIRepeatingGroupLength() {
        return cpRgLen;
    }

    /**
     * The Space Character Section Number parameter specifies the section number for the code point
     * of the space character.
     *
     * @return the space character section number
     */
    public int getSpaceCharacterSectionNumber() {
        return vsCharSn;
    }

    /**
     * The Space Character Code Point parameter specifies the code point assigned as the space
     * character; for a double-byte code page, the space character is identified with the space
     * character section parameter followed by the space character code point parameter.
     *
     * @return the space character code point
     */
    public int getSpaceCharacterCodePoint() {
        return vsChar;
    }

    /**
     * When this flag bit is set to B'1' (on), the CPI repeating groups are sorted in ascending code
     * point order. When this flag bit is set to B'0' (off), the CPI repeating groups are sorted in
     * ascending character ID order.
     *
     * @return true if the CPI repeating groups are sorted by ascending code point order
     */
    public boolean isAscendingCodePoint() {
        return isAscendingCodePoint;
    }

    /**
     * This flag is used to enable variable spacing. When the flag is B'1', the PTOCA variable space
     * increment is used whenever the code point for the variable space character is encountered.
     * Some AFP products, such as DCF, use variable spacing for text justification and therefore
     * require this flag to be B'1'.
     * <p>
     * When the flag is B'0', all PTOCA SVI control sequences are ignored and the space character is
     * printed as defined in the accompanying font whenever the variable space code point is
     * encountered. If a code page is built that has this flag as B'0', it should not be used with
     * AFP products that require the variable space function.
     * <p>
     *
     * @return true if variable spacing is enabled
     */
    public boolean isVariableSpaceEnabled() {
        return isVariableSpaceEnabled;
    }

    /**
     * To allow code pages that contain user-defined characters (that is, those characters that have
     * not been registered with IBM and assigned a GCGID value) to be used with TrueType/OpenType
     * fonts, the default character ID can be mapped to a Unicode scalar value. This function is
     * selected by specifying either X'FE' (for a single-byte code page) or X'FF' (for a double-byte
     * code page) in the CPI repeating group length field (CPC byte 9).
     *
     * @return the default Unicode index
     */
    public long getDefaultUnicodeIndex() {
        return defaultUnicodeValue;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("DefaultGCGID", defCharId));
        params.add(new ParameterAsString("isInvalidCodedCharacter", isInvalidCodedCharacter));
        params.add(new ParameterAsString("isNoPresentation", isNoPresentation));
        params.add(new ParameterAsString("isNoIncrement", isNoIncrement));
        params.add(new ParameterAsString("CPIRepeatingGroupLength", cpRgLen));
        params.add(new ParameterAsString("SpaceCharSectionNumber", vsCharSn));
        params.add(new ParameterAsString("SpaceCharCodePoint", vsChar));
        params.add(new ParameterAsString("isAscendingCodePoint", isAscendingCodePoint));
        params.add(new ParameterAsString("isVariableSpaceEnabled", isVariableSpaceEnabled));
        params.add(new ParameterAsString("DefaultUnicodeValue", defaultUnicodeValue));
        return params;
    }

    public static final class CPCBuilder implements Builder {
        @Override
        public CodePageControl build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException {
            return new CodePageControl(intro, params, context);
        }
    }
}
