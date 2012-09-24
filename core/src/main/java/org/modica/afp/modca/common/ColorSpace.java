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

package org.modica.afp.modca.common;

/**
 * A code that defines the color space and the encoding for the color specification.
 */
public enum ColorSpace {
    /**
     * RGB color space. The color value is specified with three components. Components 1, 2, and
     * 3 are unsigned binary numbers that specify the red, green, and blue intensity values, in
     * that order. ColSize1, ColSize2, and ColSize3 are non-zero and define the number of bits
     * used to specify each component. ColSize4 is reserved and should be set to zero.
     */
    RGB(0x01),
    /**
     * CMYK color space. The color value is specified with four components. Components 1, 2, 3,
     * and 4 are unsigned binary numbers that specify the cyan, magenta, yellow, and black
     * intensity values, in that order. ColSize1, ColSize2, ColSize3, and ColSize4 are non-zero
     * and define the number of bits used to specify each component.
     */
    CMYK(0x04),
    /**
     * Highlight color space. This color space defines a request for the presentation device to
     * generate a highlight color. The color value is specified with one to three components.
     */
    HILIGHT_COLOR_SPACE(0x06),
    /** CIELAB color space. */
    CIELAB(0x08),
    /** Standard OCA color space. The color value is specified with one component.*/
    STANDARD_OCA_COLORSPACE(0x40);

    private final byte id;

    private ColorSpace(int id) {
        this.id = (byte) id;
    }

    public static ColorSpace getValue(byte id) {
        for (ColorSpace cs : ColorSpace.values()) {
            if (id == cs.id) {
                return cs;
            }
        }
        throw new IllegalArgumentException(id + " is not a valid ColorSpace value");
    }
}