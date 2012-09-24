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
 * The adjustment specifies the value of additional space between graphic characters. This space is
 * in the I-direction from the end of the current character increment to the presentation position
 * of the following graphic character. When this value is positive, the adjustment is referred to as
 * an increment. When the value is negative, the adjustment is referred to as a decrement. The
 * direction specifies the direction in which the intercharacter adjustment is to be applied.
 */
public class SetIntercharacterAdjustment extends ControlSequence {

    private final int adjustment;
    private final boolean directionIsPositive;

    public SetIntercharacterAdjustment(ControlSequenceIdentifier csId, int length,
            boolean isChained, Parameters params) {
        super(csId, length, isChained);
        adjustment = (int) params.getUInt(2);
        if (length > 4) {
            directionIsPositive = params.getByte() == 0;
        } else {
            directionIsPositive = true;
        }
    }

    /**
     * The adjustment specifies the value of additional space between graphic characters. This space
     * is in the I-direction from the end of the current character increment to the presentation
     * position of the following graphic character. When this value is positive, the adjustment is
     * referred to as an increment. When the value is negative, the adjustment is referred to as a
     * decrement.
     *
     * @return the adjustment
     */
    public int getAdjustment() {
        return adjustment;
    }

    /**
     * The direction specifies the direction in which the intercharacter adjustment is to be
     * applied. Intercharacter increment, which occurs when the this returns true, is applied in the
     * positive I-direction. Intercharacter decrement, which occurs when this returns false, is
     * applied in the negative I-direction.
     *
     * @return true if the direction is positive
     */
    public boolean isDirectionPositive() {
        return directionIsPositive;
    }

    @Override
    public String getValueAsString() {
        return "move " + String.valueOf(adjustment)
                + (directionIsPositive ? "" : " negative direction");
    }
}
