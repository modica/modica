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
 * This control sequence marks the end of a string of presentation text that has been suppressed.
 * It works in conjunction with the Begin Suppression control sequence. If the value of the LID is
 * not supported or is not within the range specified by PTOCA, exception condition EC-9801 exists.
 * The standard action in this case is to ignore this control sequence and continue presentation
 * with the value determined according to the data-stream hierarchy.
 */
public class EndSuppression extends ControlSequence {

    private final byte lid;

    public EndSuppression(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        lid = params.getByte();
    }

    /**
     * The local identifier, is used to match with a corresponding BeginSuppression control
     * sequence.
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
