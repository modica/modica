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
 * This control sequence specifies an increment in the B-direction from the current baseline
 * coordinate position to a new baseline coordinate position. After execution of this control
 * sequence, presentation is resumed at the new baseline coordinate position. A positive value
 * causes movement in the B-direction, while a negative value causes movement toward the I-axis.
 * This control sequence does not modify the current inline coordinate position.
 */
public class RelativeMoveBaseline extends ControlSequence {

    public final int increment;

    public RelativeMoveBaseline(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        increment = params.getInt(2);
    }

    /**
     * The amount to move the baseline.
     *
     * @return the amount to move
     */
    public int getIncrement() {
        return increment;
    }

    @Override
    public String getValueAsString() {
        return "move " + String.valueOf(increment);
    }
}
