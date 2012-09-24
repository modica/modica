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
 * The flags on a GCGID that describe its attributes.
 */
public enum GraphicalCharacterUseFlags {
    INVALID_CODED_CHARACTER,
    NO_PRESENTATION,
    NO_INCREMENT;

    private final byte bitMask;

    private GraphicalCharacterUseFlags() {
        bitMask = (byte) (1 << 7 - this.ordinal());
    }

    /**
     * The Invalid Coded Character parameter specifies that the associated coded graphic character
     * is not valid and should not be used for processing. If this flag is off (0), the coded
     * graphic character is valid. If this flag is on (1), the coded graphic character is not valid.
     *
     * @param flag the byte flag
     * @return true if the invalid coded character flag is set
     */
    public static boolean isInvalidCodedCharacter(byte flag) {
        return (flag & INVALID_CODED_CHARACTER.bitMask) != 0;
    }

    /**
     * The No Increment parameter specifies that the character increment for the corresponding coded
     * character should be ignored. If this flag is off (0), the coded character is incrementing. If
     * this flag is on (1), the coded character is non-incrementing.
     *
     * @param flag the byte flag
     * @return true if the no presentation flag is set
     */
    public static boolean isNoPresentation(byte flag) {
        return (flag & NO_PRESENTATION.bitMask) != 0;
    }

    /**
     * The No Presentation parameter specifies that the corresponding coded character should be
     * ignored. If this flag is off (0), the coded character is presenting. If this flag is on (1),
     * the coded character is non-presenting.
     *
     * @param flag the byte flag
     * @return true if the no increment flag is set
     */
    public static boolean isNoIncrement(byte flag) {
        return (flag & NO_INCREMENT.bitMask) != 0;
    }
}
