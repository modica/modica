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
 * This control sequence marks the beginning of a new line. It increments the current baseline
 * coordinate position by the amount of the baseline increment. It sets the current inline
 * coordinate to the inline margin. Presentation is resumed at the new baseline coordinate position
 * at the inline margin.
 */
public class BeginLine extends ControlSequence {

    public BeginLine(ControlSequenceIdentifier csId, int length, boolean isChained) {
        super(csId, length, isChained);
    }

    @Override
    public String getValueAsString() {
        return "";
    }
}
