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

/**
 * The Temporary Baseline Move control sequence changes the position of the baseline without
 * changing the established baseline.
 */
public class TemporaryBaselineMove extends ControlSequence {

    private final Direction direction;
    private final boolean precision;
    private final int increment;

    public TemporaryBaselineMove(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        direction = Direction.getValue(params.getByte());
        if (length == 4) {
            precision = params.getByte() > 0;
            increment = 0;
        } else if (length == 6) {
            precision = params.getByte() > 0;
            increment = params.getInt(2);
        } else {
            precision = false;
            increment = 0;
        }
    }

    /**
     * An enumeration that describes the direction of the base line move.
     */
    public enum Direction {
        /** Do not change the baseline. */
        NO_CHANGE,
        /**
         * Return to the established baseline. Delete the temporary baseline created by TBM control
         * sequences.
         */
        RETURN_BASELINE,
        /**
         * Move the temporary baseline away from the I-axis one value of the increment, performing a
         * subscript function. The increment is applied to the current baseline coordinate, not to
         * the established baseline, and has no effect on the established baseline.
         */
        INCREMENT_BASELINE,
        /**
         * Move the temporary baseline toward the I-axis one value of the increment, performing a
         * superscript function. The increment is applied to the current baseline coordinate, not to
         * the established baseline, and has no effect on the established baseline.
         */
        DECREMENT_BASELINE;

        private final byte id;

        private Direction() {
            id = (byte) this.ordinal();
        }

        private static Direction getValue(byte id) {
            for (Direction direction : Direction.values()) {
                if (direction.id == id) {
                    return direction;
                }
            }
            throw new IllegalArgumentException("Invalid direction parameter in control sequence.");
        }
    }

    /**
     * The direction of the baseline move.
     *
     * @return the move direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * If this returns false, the receiver must accurately place and represent the character using
     * the coded font that is resident and active when the control sequence is executed. In this
     * case, the movement of the baseline is not simulated, and the character presented on the
     * shifted baseline is the same as the characters used in the surrounding text. That is, it is
     * not a character specially designed for subscripting or superscripting. However, this does not
     * prohibit changing the coded font. If this returns false, the intent is to ensure that the
     * receiver has the word processing capability of producing formal documents.
     * <p>
     * If this returns true, the movement of the baseline may be simulated using specially designed
     * subscript or superscript characters which appear smaller than the surrounding text.
     * </p>
     *
     * @return whether precision is set
     */
    public boolean getPrecision() {
        return precision;
    }

    /**
     * The amount to increment the baseline.
     *
     * @return the baseline increment
     */
    public int getIncrement() {
        return increment;
    }

    @Override
    public String getValueAsString() {
        return "direction=" + direction.toString()
                + " increment=" + increment;
    }
}
