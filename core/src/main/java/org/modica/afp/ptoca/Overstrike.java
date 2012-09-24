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
 * Overstrike is accomplished with a pair of Overstrike control sequences. A beginning OVS with a
 * non-zero value in BypassIdentity bits 4-7 activates overstrike. An ending OVS with a zero value
 * in BypassIdentity bits 4-7 deactivates overstrike. The beginning OVS control sequence immediately
 * precedes the field of text to be overstruck. It specifies the following.
 * <p>- The overstrike character</p>
 * <p>- How to place the overstrike characters in relation to the characters in the text field<p>
 * <p>- Which controlled inline white space is to be overstruck.</p>
 * The text field to be overstruck, called the overstrike field, is delimited by the beginning OVS
 * and either an ending OVS control sequence or the end of the Presentation Text object. The
 * overstrike field is a sequential string of presentation text.
 */
public class Overstrike extends ControlSequence {

    private final BypassFlags bypassFlags;
    private final int overStrikeChar;

    public Overstrike(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        bypassFlags = new BypassFlags(params.getByte());
        overStrikeChar = (int) params.getUInt(2);
    }

    /**
     * A value of false in this field indicates that the controlled white space generated as a
     * result of a Relative Move Inline control sequence is to be overstruck. A value of true in
     * this field indicates that such controlled white space is not to be overstruck. It should be
     * bypassed.
     *
     * @return whether to bypass a relative move inline
     */
    public boolean bypassRelativeMoveInline() {
        return bypassFlags.bypassRelativeMoveInline();
    }

    /**
     * A value of false in this field indicates that the controlled white space generated as a
     * result of an Absolute Move Inline control sequence is to be overstruck. A value of true in
     * this bit indicates that such controlled white space is not to be overstruck. It should be
     * bypassed.
     *
     * @return whether to bypass an absolute move inline
     */
    public boolean bypassAbsoluteMoveInline() {
        return bypassFlags.bypassAbsoluteMoveInline();
    }

    /**
     * A value of false in this field indicates that the controlled white space generated as a
     * result of space characters or variable space characters is to be overstruck. A value of true
     * in this field indicates that such controlled white space is not to be overstruck. It should
     * be bypassed.
     *
     * @return whether to bypass space characters
     */
    public boolean bypassSpaceChars() {
        return bypassFlags.bypassSpaceChars();
    }

    /**
     * The overstrike character is defined as a one-byte or two-byte code point that, when coupled
     * with the active coded font, specifies the character to be used for overstriking. Single-byte
     * code points are located in byte 6. Single-byte or double-byte character representation is
     * established by the Font Object Content Architecture.
     *
     * @return the overstriking character
     */
    public int getOverstrikeCharacter() {
        return overStrikeChar;
    }

    @Override
    public String getValueAsString() {
        return "BypassRMI=" + bypassRelativeMoveInline()
                + " BypassAMI=" + bypassAbsoluteMoveInline()
                + " BypassSpaceChars=" + bypassSpaceChars();
    }
}
