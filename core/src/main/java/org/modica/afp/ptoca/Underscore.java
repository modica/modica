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
 * The Underscore control sequence identifies text fields that are to be underscored.
 */
public class Underscore extends ControlSequence {

    private final BypassFlags bypassFlags;

    public Underscore(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        bypassFlags = new BypassFlags(params.getByte());
    }

    /**
     * Returning false indicates that the controlled white space generated as a result of a Relative
     * Move Inline control sequence is to be underscored. Returning true indicates that such
     * controlled white space is not to be underscored. It should be bypassed.
     *
     * @return whether to bypass a relative move inline
     */
    public boolean bypassRelativeMoveInline() {
        return bypassFlags.bypassRelativeMoveInline();
    }

    /**
     * Returning false indicates that the controlled white space generated as a result of an
     * Absolute Move Inline control sequence is to be underscored. Returning true indicates that
     * such controlled white space is not to be underscored. It should be bypassed.
     *
     * @return whether to bypass an absolute move inline
     */
    public boolean bypassAbsoluteMoveInline() {
        return bypassFlags.bypassAbsoluteMoveInline();
    }

    /**
     * Returning false indicates that the controlled white space generated as a result of space
     * characters or variable space characters is to be underscored. Returning true indicates that
     * such controlled white space is not to be underscored. It should be bypassed.
     *
     * @return whether to bypass space characters
     */
    public boolean bypassSpaceChars() {
        return bypassFlags.bypassSpaceChars();
    }

    @Override
    public String getValueAsString() {
        return "BypassRMI=" + bypassRelativeMoveInline()
                + " BypassAMI=" + bypassAbsoluteMoveInline()
                + " BypassSpaceChars=" + bypassSpaceChars();
    }
}
