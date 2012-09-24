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

package org.modica.afp.ioca;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.ColorSpace;
import org.modica.common.ByteUtils;

/**
 * This optional self-defining field specifies a color value and defines the color space and
 * encoding for that value. This SDF is applicable only to significant image points of bilevel
 * images with zero LUT-IDs.
 */
public class SetExtendedBilevelImageColor implements SelfDefiningField {

    private final int length;
    private final ColorSpace colourSpace;
    private final int colSize1;
    private final int colSize2;
    private final int colSize3;
    private final int colSize4;
    private final byte[] color;

    public SetExtendedBilevelImageColor(Parameters params) {
        int position = params.getPosition();
        // The length does not include the length field
        length = params.getInt(1) + 1;
        colourSpace = ColorSpace.getValue(params.getByte());
        colSize1 = (int) params.getUInt(2);
        colSize2 = (int) params.getUInt(2);
        colSize3 = (int) params.getUInt(2);
        colSize4 = (int) params.getUInt(2);
        int colourLength = length - (params.getPosition() - position);
        color = params.getByteArray(colourLength);
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public byte getId() {
        return (byte) 0xF4;
    }

    public ColorSpace getColourSpace() {
        return colourSpace;
    }

    public int getColourSize1() {
        return colSize1;
    }

    public int getColourSize2() {
        return colSize2;
    }

    public int getColourSize3() {
        return colSize3;
    }

    public int getColourSize4() {
        return colSize4;
    }

    public int getColor() {
        ByteUtils byteUtils = ByteUtils.getBigEndianUtils();
        return (int) byteUtils.bytesToUnsignedInt(color);
    }

    @Override
    public String toString() {
        return "SetExtendedBilevelImageColor colourSpace=" + colourSpace;
    }

    @Override
    public String getName() {
        return "SetExtendedBilevelImageColour";
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ColourSpace", colourSpace));
        params.add(new ParameterAsString("colourSize1", colSize1));
        params.add(new ParameterAsString("colourSize2", colSize2));
        params.add(new ParameterAsString("colourSize3", colSize3));
        params.add(new ParameterAsString("colourSize4", colSize4));
        return params;
    }
}
