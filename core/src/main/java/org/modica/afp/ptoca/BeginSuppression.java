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
import org.modica.common.ByteUtils;

/**
 * This control sequence marks the beginning of a string of presentation text that may be suppressed
 * from the visible output. It is activated by a local identifier, LID. This control sequence works
 * in conjunction with the End Suppression control sequence, which also contains a LID. If the LID
 * in this control sequence has been activated for the current Presentation Text object in the data
 * stream hierarchy, the string of presentation text between this control sequence and the next End
 * Suppression control sequence with the same LID does not appear in the visible output. Even though
 * the text does not appear, all control sequences within the suppressed field are executed, and the
 * I-coordinate and B-coordinate are updated as if the text had appeared. Only the actual
 * presentation of the graphic characters and rules is suppressed.
 */
public class BeginSuppression extends ControlSequence {

    private final byte lid;

    public BeginSuppression(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        lid = params.getByte();
    }

    /**
     * The local identifier, is used to match with a corresponding EndSuppression control sequence.
     *
     * @return the local identifier
     */
    public byte getLid() {
        return lid;
    }

    @Override
    public String getValueAsString() {
        return "lid=0x" + ByteUtils.bytesToHex(lid);
    }
}
