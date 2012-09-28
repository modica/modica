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
