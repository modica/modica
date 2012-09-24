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
 * The Weight Class parameter indicates the visual weight (degree or thickness of strokes) of the
 * collection of graphic characters in the font resource. These values are assigned by a font
 * designer, and the visual effect is not defined in FOCA.
 */
public enum FontWeightClass {
    ULTRALIGHT,
    EXTRALIGHT,
    LIGHT,
    SEMILIGHT,
    MEDIUM,
    SEMIBOLD,
    BOLD,
    EXTRABOLD,
    ULTRABOLD;

    private final byte id;

    private FontWeightClass() {
        id = (byte) (this.ordinal() + 1);
    }

    public static FontWeightClass getValue(byte id) {
        for (FontWeightClass weight : FontWeightClass.values()) {
            if (weight.id == id) {
                return weight;
            }
        }
        throw new IllegalArgumentException(id + " is not a valid FontWeightClass");
    }
}
