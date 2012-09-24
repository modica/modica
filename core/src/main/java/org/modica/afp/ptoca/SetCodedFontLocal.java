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

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.Parameters;
import org.modica.common.ByteUtils;

/**
 * This control sequence specifies a local identifier, LID, which is used by the font resource to
 * access a coded font for presentation of subsequent text in the current Presentation Text object.
 * The current presentation position is not changed by this control sequence.
 */
public class SetCodedFontLocal extends ControlSequence {

    private final byte lid;

    public SetCodedFontLocal(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params, Context ctx) {
        super(csId, length, isChained);
        lid = params.getByte();
        ctx.put(ContextType.PTOCA_SET_CODED_FONT_LOCAL, lid);
    }

    /**
     * The LID is equated to a Graphic Character Set Global Identifier (GCSGID), Code Page Global
     * Identifier (CPGID), Font Global Identifier (FGID), character rotation, and font modification
     * parameters by a mapping function in the controlling environment.
     *
     * @return the local identifier
     */
    public byte getLocalId() {
        return lid;
    }

    @Override
    public String getValueAsString() {
        return "localid=0x" + ByteUtils.bytesToHex(lid);
    }
}
