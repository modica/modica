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

package org.modica.afp.ptoca;

import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.ColorSpace;
import org.modica.common.ByteUtils;

/**
 * The Set Extended Text Color control sequence specifies a color value and defines the color space
 * and encoding for that value. The specified color value is applied to foreground areas of the text
 * presentation space.
 */
public class SetExtendedTextColor extends ControlSequence {

    private final ColorSpace colourSpace;
    private final int colourSize1;
    private final int colourSize2;
    private final int colourSize3;
    private final int colourSize4;
    private final byte[] color;

    public SetExtendedTextColor(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        int position = params.getPosition();
        params.getByte(); // reserved
        colourSpace = ColorSpace.getValue(params.getByte());
        params.skip(4);
        colourSize1 = (int) params.getUInt(1);
        colourSize2 = (int) params.getUInt(1);
        colourSize3 = (int) params.getUInt(1);
        colourSize4 = (int) params.getUInt(1);
        // length and type fields are included in the length
        int colourLength = length - (params.getPosition() - position) - 2;
        color = params.getByteArray(colourLength);
    }

    public ColorSpace getColourSpace() {
        return colourSpace;
    }

    public int getColourSize1() {
        return colourSize1;
    }

    public int getColourSize2() {
        return colourSize2;
    }

    public int getColourSize3() {
        return colourSize3;
    }

    public int getColourSize4() {
        return colourSize4;
    }

    public long getColor() {
        ByteUtils byteUtils = ByteUtils.getBigEndianUtils();
        return byteUtils.bytesToUnsignedInt(color);
    }

    @Override
    public String getValueAsString() {
        return "ColourSpace=" + colourSpace.toString()
                + " colSize1=" + colourSize1
                + " colSize2=" + colourSize2
                + " colSize3=" + colourSize3
                + " colSize4=" + colourSize4;
    }
}
