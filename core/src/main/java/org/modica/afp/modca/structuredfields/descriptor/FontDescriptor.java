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

import org.modica.afp.foca.FontWeightClass;
import org.modica.afp.foca.FontWidthClass;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.EbcdicStringHandler;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;
import org.modica.common.ByteUtils;

/**
 * The Font Descriptor (FND) specifies the overall characteristics of a font character set.
 */
public class FontDescriptor extends StructuredFieldWithTriplets {

    private final String typeFcDesc;
    private final FontWeightClass fontWeight;
    private final FontWidthClass fontWidth;
    private final int maxPtSize;
    private final int nomPtSize;
    private final int minPtSize;
    private final int maxHSize;
    private final int nomHSize;
    private final int minHSize;
    private final byte designGeneralClass;
    private final byte designSubClass;
    private final byte designSpecificClass;
    private final boolean isItalic;
    private final boolean hasUnderScore;
    private final boolean isHollow;
    private final boolean isOverstruck;
    private final int gcsgid;
    private final int fgid;

    FontDescriptor(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        typeFcDesc = params.getString(32, EbcdicStringHandler.DEFAULT_CPGID);
        fontWeight = FontWeightClass.getValue(params.getByte());
        fontWidth = FontWidthClass.getValue(params.getByte());
        maxPtSize = (int) params.getUInt(2);
        nomPtSize = (int) params.getUInt(2);
        minPtSize = (int) params.getUInt(2);
        maxHSize = (int) params.getUInt(2);
        nomHSize = (int) params.getUInt(2);
        minHSize = (int) params.getUInt(2);
        designGeneralClass = params.getByte();
        designSubClass = params.getByte();
        designSpecificClass = params.getByte();
        params.skip(15);
        byte designFlag = params.getByte();
        params.getByte();
        isItalic = DesignFlags.isItalic(designFlag);
        hasUnderScore = DesignFlags.hasUnderscore(designFlag);
        isHollow = DesignFlags.isHollow(designFlag);
        isOverstruck = DesignFlags.isOverstruck(designFlag);
        params.skip(10);
        gcsgid = (int) params.getUInt(2);
        fgid = (int) params.getUInt(2);
    }

    /**
     * The fonts design flags.
     */
    private enum DesignFlags {
        ITALIC,
        UNDERSCORE,
        RESERVED,
        HOLLOW,
        OVERSTRUCK;

        private final byte bitMask;

        private DesignFlags() {
            bitMask = (byte) (1 << 7 - this.ordinal());
        }

        private static boolean isItalic(byte flag) {
            return (flag & ITALIC.bitMask) != 0;
        }

        private static boolean hasUnderscore(byte flag) {
            return (flag & UNDERSCORE.bitMask) != 0;
        }

        private static boolean isHollow(byte flag) {
            return (flag & HOLLOW.bitMask) != 0;
        }

        private static boolean isOverstruck(byte flag) {
            return (flag & OVERSTRUCK.bitMask) != 0;
        }
    }

    /**
     * This parameter (which was formerly called “Typeface Name”) contains descriptive information
     * including the Family Name and, when appropriate, the supported language complement. Examples
     * include “Helvetica Cyrillic Greek”, “Times New Roman”, and “Letter Gothic Latin1”.
     *
     * @return the typeface description
     */
    public String getTypefaceDescription() {
        return typeFcDesc;
    }

    /**
     * The Weight Class parameter indicates the visual weight (degree or thickness of strokes) of
     * the collection of graphic characters in the font resource. These values are assigned by a
     * font designer, and the visual effect is not defined in FOCA.
     *
     * @return the font weight class
     */
    public FontWeightClass getFontWeight() {
        return fontWeight;
    }

    /**
     * The Width Class parameter indicates a relative change from the normal aspect ratio
     * (width-to-height ratio) as specified by a font designer for the character shapes in a font.
     *
     * @return the font width
     */
    public FontWidthClass getFontWidth() {
        return fontWidth;
    }

    /**
     * The Maximum Vertical Font Size parameter specifies the maximum vertical size for scaling
     * purposes as specified by a font designer.
     *
     * @return the maximum vertical font size
     */
    public int getMaximumVerticalFontSize() {
        return maxPtSize;
    }

    /**
     * The Nominal Vertical Font Size parameter specifies the vertical size (design size is the
     * term used in the ISO/IEC 9541 Font Information Interchange standard) of the font as specified
     * by a font designer. The Nominal Vertical Font Size parameter specifies the nominal size for
     * which the character-metric values of this font are defined.
     *
     * @return the nominal vertical font size
     */
    public int getNominalVerticalFontSize() {
        return nomPtSize;
    }

    /**
     * The Minimum Vertical Font Size parameter specifies the minimum vertical size for scaling
     * purposes as specified by a font designer.
     *
     * @return the minimum vertical font size
     */
    public int getMinimumVerticalFontSize() {
        return minPtSize;
    }

    /**
     * The Maximum Horizontal Font Size parameter specifies the maximum horizontal size for scaling,
     * as specified by a font designer. This value generally corresponds to the maximum character
     * escapement value of a space character (SP010000) in a font of the Nominal Vertical Font Size.
     * The value is used to calculate a maximum scaling ratio which can be applied to the width of
     * all characters of the font.
     *
     * @return the maximum horizontal font size
     */
    public int getMaximumHorizontalFontSize() {
        return maxHSize;
    }

    /**
     * The Nominal Horizontal Font Size parameter specifies the nominal horizontal size for scaling.
     * This parameter corresponds to the character escapement value of the space character
     * (SP010000) expressed in fixed units of measure.
     *
     * @return the nominal horizontal font size
     */
    public int getNominalHorizontalFontSize() {
        return nomHSize;
    }

    /**
     * The Minimum Horizontal Font Size parameter specifies the minimum horizontal size for scaling,
     * as specified by a font designer. The value of the parameter corresponds to the minimum
     * character escapement value of a space character (SP010000) in a font of the Nominal Vertical
     * Font Size. The value is used to calculate a minimum scaling ratio which can be applied to the
     * width of all characters of the font. This parameter is often used for fonts in graphic
     * displays or in Asian languages where scaling is permitted in horizontal and vertical
     * directions.
     *
     * @return the minimum horizontal font size
     */
    public int getMinimumHorizontalFontSize() {
        return minHSize;
    }

    /**
     * The Design General Class parameter specifies the ISO Font Standard General Classification of
     * the font family design. This parameter is intended for use in selecting an alternate font
     * when the requested font is not available. The General Class parameter is the least specific,
     * the Subclass parameter more specific, and the Specific Group parameter the most specific of
     * the Design Class parameters.
     *
     * @return the design general class (ISO)
     */
    public byte getDesignGeneralClass() {
        return designGeneralClass;
    }

    /**
     * The Design Subclass parameter specifies the ISO Font Standard Subclass of the font family
     * design. This parameter is intended for use in selecting an alternate font when the requested
     * font is not available. The General Class parameter is the least specific, the Subclass
     * parameter more specific, and the Specific Group parameter the most specific of the Design
     * Class parameters.
     *
     * @return the design sub class (ISO)
     */
    public byte getDesignSubClass() {
        return designSubClass;
    }

    /**
     * The Design Specific Group parameter specifies the ISO (Organization) Font Standard Specific
     * Group of the font family design. This parameter is intended for use in selecting an alternate
     * font when the requested font is not available. The General Class parameter is the least
     * specific, the Subclass parameter more specific, and the Specific Group parameter the most
     * specific of the Design Class parameters.
     *
     * @return the design specific class (ISO)
     */
    public byte getDesignSpecificClass() {
        return designSpecificClass;
    }

    /**
     * The Italics parameter specifies that the graphic characters are designed with a clockwise
     * incline. If this flag is off (0), the graphic character shapes have no clockwise italic
     * design. If this flag is on (1), the character shapes have a clockwise italic design.
     *
     * @return true if the font is an italic font
     */
    public boolean isItalic() {
        return isItalic;
    }

    /**
     * The Underscored Font parameter specifies that the graphic character pattern data of this font
     * resource contain underscores as part of the character shape. If this flag is off (0), the
     * graphic character patterns in the font are not underscored. If this flag is on (1), they are
     * underscored.
     *
     * @return true if the font has an underscore
     */
    public boolean hasUnderscore() {
        return hasUnderScore;
    }

    /**
     * The Hollow Font parameter specifies that the graphic characters of the font appear with only
     * the outer edges of the strokes. If the Hollow font flag is off (0), the graphic characters do
     * not have a hollow appearance. If this flag is on (1), the graphic characters do have a hollow
     * appearance.
     *
     * @return true if the font is a hollow font, otherwise this font is a solid font
     */
    public boolean isHollow() {
        return isHollow;
    }

    /**
     * The Overstruck Font parameter specifies that the graphic characters of the font appear as
     * though over-struck by another graphic character (often a hyphen graphic character). If this
     * flag is off (0), the graphic characters in the font are not overstruck. If this flag is on
     * (1), they are overstruck.
     *
     * @return true if the font is overstruck
     */
    public boolean isOverstruck() {
        return isOverstruck;
    }

    /**
     * This is the GCSGID of the font character set. It may represent a set of characters that is
     * either smaller or larger than the set of characters contained in the code page associated
     * with this font character set in a coded font reference. When creating a coded font reference,
     * the GCSGID that represents the smaller of the two sets of characters (font character set or
     * code page character set) should be used.
     *
     * @return the GCSGID
     */
    public int getGcsgid() {
        return gcsgid;
    }

    /**
     * The Font Typeface Global Identifier parameter (usually called Font Global Identifier, FGID)
     * specifies the unique number assigned to the font typeface. The Font Typeface Global ID
     * numbers that are supported are specified in IBM product documentation. Font Typeface Global
     * IDs 1 through 65,279 are reserved for assignment by IBM.
     *
     * @return the FGID
     */
    public int getFgid() {
        return fgid;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("TypefaceDescription", typeFcDesc));
        params.add(new ParameterAsString("FontWeight", fontWeight));
        params.add(new ParameterAsString("FontWidth", fontWidth));
        params.add(new ParameterAsString("MaxPointSize", maxPtSize));
        params.add(new ParameterAsString("NominalPointSize", nomPtSize));
        params.add(new ParameterAsString("MinPointSize", minPtSize));
        params.add(new ParameterAsString("MaxHorizontalSize", maxHSize));
        params.add(new ParameterAsString("NominalHorizontalSize", nomHSize));
        params.add(new ParameterAsString("MinHorizontalSize", minHSize));
        params.add(new ParameterAsString("DesignGeneralClass", ByteUtils.bytesToHex(designGeneralClass)));
        params.add(new ParameterAsString("DesignSubClass", ByteUtils.bytesToHex(designSubClass)));
        params.add(new ParameterAsString("DesignSpecificClass", ByteUtils.bytesToHex(designSpecificClass)));
        params.add(new ParameterAsString("isItalic", isItalic));
        params.add(new ParameterAsString("isHollow", isHollow));
        params.add(new ParameterAsString("isOverStruck", isOverstruck));
        params.add(new ParameterAsString("GCSGID", gcsgid));
        params.add(new ParameterAsString("FGID", fgid));
        return params;
    }

    public static final class FNDBuilder implements Builder {
        @Override
        public FontDescriptor build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new FontDescriptor(intro, TripletHandler.parseTriplet(params, 80, context), params);
        }
    }
}
