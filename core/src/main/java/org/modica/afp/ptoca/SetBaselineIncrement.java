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
 * The Set Baseline Increment control sequence specifies the increment to be added to the current
 * baseline coordinate when a Begin Line control sequence is executed. This is a modal control
 * sequence.
 */
public class SetBaselineIncrement extends ControlSequence {

    private final int increment;

    public SetBaselineIncrement(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        increment = params.getInt(2);
    }

    /**
     * Specifies an increment in the positive B-direction from the current baseline coordinate
     * position to a new established baseline coordinate position for subsequent presentation text
     * in the current Presentation Text object. The increment is applied when a Begin Line control
     * sequence is executed.
     *
     * @return the baseline increment
     */
    public int getIncrement() {
        return increment;
    }

    @Override
    public String getValueAsString() {
        return "move " + String.valueOf(increment);
    }
}
