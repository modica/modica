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
 * This control sequence specifies a displacement in the B-direction from the I-axis of the object
 * space to a new baseline coordinate position. After execution of this control sequence,
 * presentation is resumed at the new baseline coordinate position. This control sequence does not
 * modify the current inline coordinate position.
 */
public class AbsoluteMoveBaseline extends ControlSequence {

    private final int displacement;

    public AbsoluteMoveBaseline(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        displacement = (int) params.getUInt(2);
    }

    /**
     * The amount to displace the baseline.
     *
     * @return the amount to displace
     */
    public int getDisplacement() {
        return displacement;
    }

    @Override
    public String getValueAsString() {
        return "moveto " + String.valueOf(displacement);
    }
}
