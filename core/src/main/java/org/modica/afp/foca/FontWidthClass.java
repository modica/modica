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

package org.modica.afp.foca;

/**
 * The Width Class parameter indicates a relative change from the normal aspect ratio
 * (width-to-height ratio) as specified by a font designer for the character shapes in a font.
 * Although every character in a font might have a different numeric aspect ratio, each character in
 * a font of normal width has a relative aspect ratio of one.
 * <p>
 * When a new type style is created with a different width class (either by a font designer or by
 * some automated means) the relative aspect ratio of the characters in the new font is some
 * percentage greater or less than those same characters in the normal font. It is this difference
 * that this parameter specifies.
 * </p>
 * <p>
 * The font designer assigns a width class designation for each design variation of a particular
 * typeface. However, if a font design is to be varied by automated means, percentage changes are
 * allowed from normal to each of the width class values. For uniformity, when IBM fonts are varied
 * by automated means, a percentage change from normal is assigned to each of the width class values
 * defined above.
 * </p>
 * <p>
 * The font designer normally assigns the width class values, and the corresponding percentage
 * difference from normal might not apply. Comparing the designated percentage values with the
 * aspect-ratio differences for several designed fo ratios varied by as much as 50 percent from the
 * designated percentage.
 * <p>
 */
public enum FontWidthClass {
    /** Ultracondensed, which is 50 percent of normal */
    ULTRACONDENSED,
    /** Extracondensed, which is 62.5 percent of normal */
    EXTRACONDENSED,
    /** Condensed, which is 75 percent of normal */
    CONDENSED,
    /** Semicondensed, which is 87.5 percent of normal */
    SEMICONDENSED,
    /** Normal (medium), which is 100 percent of normal */
    MEDIUM,
    /** Semiexpanded, which is 112.5 percent of normal */
    SEMIEXPANDED,
    /** Expanded, which is 125 percent of normal */
    EXPANDED,
    /** Extraexpanded, which is 150 percent of normal */
    EXTRAEXPANDED,
    /** Ultraexpanded, which is 200 percent of normal */
    ULTRAEXPANDED;

    private final byte id;

    private FontWidthClass() {
        id = (byte) (this.ordinal() + 1);
    }

    public static FontWidthClass getValue(byte id) {
        for (FontWidthClass width : FontWidthClass.values()) {
            if (width.id == id) {
                return width;
            }
        }
        throw new IllegalArgumentException(id + " is not a valid FontWeightClass");
    }
}
