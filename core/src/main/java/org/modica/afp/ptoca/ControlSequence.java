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

/**
 * A PTOCA construct that describe text presentation commands.
 */
public abstract class ControlSequence {

    private final ControlSequenceIdentifier csId;
    private final int length;
    private final boolean isChained;

    public ControlSequence(ControlSequenceIdentifier csId, int length, boolean isChained) {
        this.csId = csId;
        this.length = length;
        this.isChained = isChained;
    }

    /**
     * Returns an enumeration of the control sequence identifier.
     *
     * @return the control sequence identifier
     */
    public ControlSequenceIdentifier getControlSequenceIdentifier() {
        return csId;
    }

    /**
     * Control sequences can be chained such that the control sequence prefix and class bytes are
     * not prepended onto each of the elements in a sequence. If this control sequence is in a chain
     * this will return true, if however, it is at the end of a chain, false will be returned.
     *
     * @return true if this control sequence is in a chain
     */
    public boolean isChained() {
        return isChained;
    }

    /**
     * The number of bytes that comprise this control sequence.
     *
     * @return the length in bytes
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the value that this control sequence wraps in the form of a String.
     *
     * @return the value as a String
     */
    public abstract String getValueAsString();

    @Override
    public String toString() {
        return csId.getName() + ": " + getValueAsString();
    }

    double getFraction(byte b) {
        double fraction = 0;
        for (int i = 7; i >= 0; i--) {
            if ((b & (1 << i)) > 0) {
                fraction += 1.0 / (1 << (8 - i));
            }
        }
        return fraction;
    }
}
