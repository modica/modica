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
import java.net.MalformedURLException;
import java.util.EnumSet;
import java.util.List;

import org.modica.afp.foca.FontFlag;
import org.modica.afp.foca.PatternAlignment;
import org.modica.afp.foca.PatternTechIdentifier;
import org.modica.afp.foca.UnitsPerUnitBase;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.PresentationSpaceUnits;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Font Control (FNC) structured field provides defaults and information about the font
 * character set.
 */
public class FontControl extends StructuredFieldWithTriplets {
    
    private final PatternTechIdentifier patTech;
    private final EnumSet<FontFlag> fontFlags;
    private final PresentationSpaceUnits xUnitBase;
    private final PresentationSpaceUnits yUnitBase;
    private final UnitsPerUnitBase xUnitsPerBase;
    private final UnitsPerUnitBase yUnitsPerBase;
    private final int maxCharacterBoxWidth;
    private final int maxCharacterBoxHeight;
    private final int fnoRepeatingGroupLength;
    private final int fniRepeatingGroupLength;
    private final PatternAlignment patAlign;
    private final int rasterPatternDataCount;
    private final int fnpRepeatingGroupLength;
    private final int fnmRepeatingGroupLength;

    private PresentationSpaceUnits resXUBase;
    private PresentationSpaceUnits resYUBase;
    private UnitsPerUnitBase xfrUnits;
    private UnitsPerUnitBase yfrUnits;
    private int outlinePatternDataCount;
    private int fnnRepeatingGroupLength;
    private int fnnDataCount;
    private int fnnNameCount;

    public FontControl(StructuredFieldIntroducer intro, Parameters params, List<Triplet> triplets) {
        super(intro, triplets);
        checkAndGetByte(params, 0x01);
        patTech = PatternTechIdentifier.getValue(params.getByte());
        checkAndGetByte(params, 0x00);
        fontFlags = FontFlag.getSetBits(params.getByte());
        xUnitBase = PresentationSpaceUnits.getValue(params.getByte());
        yUnitBase = PresentationSpaceUnits.getValue(params.getByte());
        assert xUnitBase == yUnitBase;
        xUnitsPerBase = UnitsPerUnitBase.getValue(params.getByte(), params.getByte());
        yUnitsPerBase = UnitsPerUnitBase.getValue(params.getByte(), params.getByte());
        assert xUnitsPerBase == yUnitsPerBase;
        maxCharacterBoxWidth = params.getInt(2);
        maxCharacterBoxHeight = params.getInt(2);
        fnoRepeatingGroupLength = checkAndGetByte(params, 0x1A);
        fniRepeatingGroupLength = params.getByte();
        assert fniRepeatingGroupLength == 0x0A || fniRepeatingGroupLength == 0x1C;
        patAlign = PatternAlignment.getValue(params.getByte());
        rasterPatternDataCount = params.getInt(3);
        fnpRepeatingGroupLength = params.getByte();
        assert fnpRepeatingGroupLength == 0x16;
        fnmRepeatingGroupLength = params.getInt(1);
        assert fnmRepeatingGroupLength == 0 || fnmRepeatingGroupLength == 0x08;
        if (params.hasMoreBytes()) {
            checkAndGetByte(params, 0x00);
        }
        if (params.hasMoreBytes()) {
            checkAndGetByte(params, 0x00);
        }
        if (params.hasMoreBytes()) {
            xfrUnits = UnitsPerUnitBase.getValue(params.getByte(), params.getByte());
        }
        if (params.hasMoreBytes()) {
            yfrUnits = UnitsPerUnitBase.getValue(params.getByte(), params.getByte());
        }
        if (params.hasMoreBytes()) {
            outlinePatternDataCount = params.getInt(4);
        }
        if (params.hasMoreBytes()) {
            checkAndGetByte(params, 0x00);
            checkAndGetByte(params, 0x00);
            checkAndGetByte(params, 0x00);
        }
        if (params.hasMoreBytes()) {
            fnnRepeatingGroupLength = checkAndGetByte(params, 0x0C);
        }
        if (params.hasMoreBytes()) {
            fnnDataCount = params.getInt(4);
        }
        if (params.hasMoreBytes()) {
            fnnNameCount = params.getInt(2);
        }
    }

    private byte checkAndGetByte(Parameters params, int expected) {
        byte b = params.getByte();
        assert (byte) expected == b;
        return b;
    }

    /**
     * Returns the pattern tech identifier.
     *
     * @return the pattern tech id
     */
    public PatternTechIdentifier getPatternTechId() {
        return patTech;
    }

    /**
     * Returns the font flags set for this font.
     *
     * @return the font flags
     */
    public EnumSet<FontFlag> getFontFlags() {
        return fontFlags;
    }

    /**
     * Returns the units of measure for the x-axis.
     *
     * @return the x-axis base unit
     */
    public PresentationSpaceUnits getXUnitBase() {
        return xUnitBase;
    }

    /**
     * Returns the units of measure for the y-axis.
     *
     * @return the y-axis base unit
     */
    public PresentationSpaceUnits getYUnitBase() {
        return yUnitBase;
    }

    /**
     * Returns the resolution of the x-axis base units.
     *
     * @return the x-axis resolution
     */
    public UnitsPerUnitBase getXUnitsPerBase() {
        return xUnitsPerBase;
    }

    /**
     * Returns the resolution of the y-axis base units.
     *
     * @return the y-axis resolution
     */
    public UnitsPerUnitBase getYUnitsPerBase() {
        return yUnitsPerBase;
    }

    /**
     * The Maximum Character Box Width parameter specifies the width of uniform character boxes or
     * the maximum width of variable character boxes, depending on the value of the Uniform
     * Character Box parameter.
     * <p>
     * If the Uniform Character Box parameter is off (0), the Maximum Character Box Width value
     * specifies the width of the widest character box in the font, and the Character Box Width
     * parameter specifies the width of each individual character box. This parameter can be used to
     * determine if the character, when positioned, fits in the presentation space.
     * </p>
     *
     * @return the maximum character box width
     */
    public int getMaxCharacterBoxWidth() {
        return maxCharacterBoxWidth;
    }

    /**
     * The Maximum Character Box Height parameter specifies the height of uniform character boxes
     * or the maximum height of variable character boxes, depending on the value of the Uniform
     * Character Box parameter.
     * <p>
     * If the Uniform Character Box parameter is off (0), this parameter specifies the maximum
     * height of any character box in a font, and the Character Box Height parameter specifies the
     * height of each character box in a font. This parameter can be used to determine if the
     * character, when positioned, fits in the presentation space.
     *
     * @return the max character box height
     */
    public int getMaxCharacterBoxHeight() {
        return maxCharacterBoxHeight;
    }

    /**
     * This is a control parameter, used to manage the data structures. The value contained in this
     * parameter defines the length of the repeating group used in the Font Orientation (FNO)
     * structured field. The value is a constant, set to X'1A'
     *
     * @return the FNO repeating group length
     */
    public int getFNORepeatingGroupLength() {
        return fnoRepeatingGroupLength;
    }

    /**
     * This is a control parameter, used to manage the data structures. The value contained in this
     * parameter defines the length of the repeating group used in the Font Index (FNI) structured
     * field.
     * <p>
     * For raster technology fonts, this value must be X'1C'. For outline technology fonts, this
     * value may be X'0A' or X'1C'.
     * <p>
     *
     * @return the FNI repeating group length
     */
    public int getFNIRepeatingGroupLength() {
        return fniRepeatingGroupLength;
    }

    /**
     * The boundary alignments for the raster patterns. The codes X'00', X'02', and X'03' indicate
     * one-byte, four-byte, and eight-byte alignments, respectively. The choice is left to the font
     * designer for a font containing raster patterns. To derive actual pattern data addresses, the
     * pattern data address in bytes 4–7 in the Font Patterns Map (FNM) repeating groups must be
     * multiplied by 1, 4, or 8, respectively.
     *
     * @return the pattern alignment
     */
    public PatternAlignment getPatternAlignment() {
        return patAlign;
    }

    /**
     * The total number of data bytes for all the Font Patterns (FNG) structured fields in this font
     * character set, when the pattern technology identifier is set to X'05'. Must be set to
     * X'000000' if the font does not contain raster patterns, or the pattern technology identifier
     * is not X'05'.
     *
     * @return the raster pattern data count
     */
    public int getRasterPatternDataCount() {
        return rasterPatternDataCount;
    }

    /**
     * This is a control parameter, used to manage the data structures. The value contained in this
     * parameter defines the length of the repeating group used in the Font Position (FNP)
     * structured field. The value is a constant, set to X'16'.
     *
     * @return the FNP repeating group length
     */
    public int getFNPRepeatingGroupLength() {
        return fnpRepeatingGroupLength;
    }

    /**
     * FNM Repeating Group Length This is a control parameter, used to manage the data structures.
     * The value contained in this parameter defines the length of the repeating group used in the
     * Font Position (FNP) structured field. The value is set to X'00' if the font contains outline
     * font data in the FNGs. Otherwise it contains X'08'.
     *
     * @return the FNM repeating group length
     */
    public int getFNMRepeatingGroupLength() {
        return fnmRepeatingGroupLength;
    }

    /**
     * This parameter is optional for raster fonts, but is required if the FNC structured field
     * includes outline font descriptive information and describes the shape x-axis base unit.
     *
     * @return the x-axis base unit for shapes
     */
    public PresentationSpaceUnits getXShapeBaseUnits() {
        return resXUBase;
    }

    /**
     * This parameter is optional for raster fonts, but is required if the FNC structured field
     * includes outline font descriptive information and describes the shape y-axis base unit.
     *
     * @return the y-axis base unit for shapes
     */
    public PresentationSpaceUnits getYShapeBaseUnits() {
        return resYUBase;
    }

    /**
     * This parameter is optional for raster fonts, but is required if the FNC structured field
     * includes outline font descriptive information and describes the x-axis shape resolution. 
     *
     * @return the x-axis shape resolution
     */
    public UnitsPerUnitBase getXShapeResolution() {
        return xfrUnits;
    }

    /**
     * This parameter is optional for raster fonts, but is required if the FNC structured field
     * includes outline font descriptive information and describes the y-axis shape resolution. 
     *
     * @return the y-axis shape resolution
     */
    public UnitsPerUnitBase getYShapeResolution() {
        return yfrUnits;
    }

    /**
     * Length, in bytes, of the font pattern data when the pattern technology identifier is set to
     * X'1E' or X'1F'. This parameter is used only when the pattern technology identifier is not
     * equal to X'05'.
     *
     * @return the outline pattern data count
     */
    public int getOutlinePatternDataCount() {
        return outlinePatternDataCount;
    }

    /**
     * This is a control parameter, used to manage the data structures. The value contained in this
     * parameter defines the length of the repeating group used in the Font Name Map (FNN)
     * structured field. Used when the pattern technology identifier is not equal to X'05'.
     *
     * @return the FNN repeating group length
     */
    public int getFNNRepeatingGroupLength() {
        return fnnRepeatingGroupLength;
    }

    /**
     * This is a control parameter used to manage the data structures and specify the number of data
     * bytes in the FNN structured fields. This field is included when the FNGs contain outline font
     * data; when the pattern technology identifier is equal to X'1E', or X'1F'.
     *
     * @return the FNN data count
     */
    public int getFNNDataCount() {
        return fnnDataCount;
    }

    /**
     * This is a control parameter used to manage the data structures and specify the number of IBM
     * character names (GCGIDs) mapped to outline font character names in the FNN structured fields.
     * This field is included when the FNGs contain outline font data; when the pattern technology
     * identifier is equal to X'1E', or X'1F'.
     *
     * @return the FNN name count
     */
    public int getFNNNameCount() {
        return fnnNameCount;
    }

    @Override
    public List<ParameterAsString> getParameters() {

        return null;
    }

    public static final class FNCBuilder implements Builder {
        @Override
        public StructuredField build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new FontControl(intro, params, TripletHandler.parseTriplet(params, 42, context));
        }
    }
}
