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
 * The Pattern Data Alignment Code parameter specifies the alignment of the beginning of each
 * character’s pattern data. The code values assigned to this parameter represent the exponent of
 * the base 2 corresponding to the byte alignment. For example, a code value of 2 means raise the
 * base, 2, to the second power to get the Pattern Data Alignment Value of 4 bytes, a full word.
 * <p>
 * Use of the Pattern Data Alignment parameter allows flexibility in aligning pattern data in
 * storage to permit use of different computing systems with differing abilities in the degree of
 * fineness in addressing storage.
 * </p>
 */
public enum PatternAlignment {
    ONE_BYTE(0),
    FOUR_BYTE(4),
    EIGHT_BYTE(8);

    private final int numberOfBytes;

    private PatternAlignment(int numberOfBytes) {
        this.numberOfBytes = numberOfBytes;
    }

    public int getNumberOfBytes() {
        return numberOfBytes;
    }

    public static PatternAlignment getValue(int value) {
        for (PatternAlignment patAlign : PatternAlignment.values()) {
            if (patAlign.numberOfBytes == value) {
                return patAlign;
            }
        }
        throw new IllegalArgumentException("An invalid pattern alignment value was given: " + value);
    }
}
