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
import org.modica.afp.modca.common.NamedColor;

/**
 * The Set Text Color control sequence specifies a color attribute for the foreground areas of the
 * text presentation space.
 */
public class SetTextColor extends ControlSequence {

    private final NamedColor foregroundColour;
    private final boolean precision;

    public SetTextColor(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        foregroundColour = NamedColor.getValue(params);
        if (length > 4) {
            precision = params.getByte() > 0;
        } else {
            precision = false;
        }
        if (precision && foregroundColour == null) {
            throw new IllegalStateException("An invalid colour was given in this PTOCA control" +
                    " sequence.");
        }
    }

    /**
     * Gets the colour that has been specified for the foreground.
     *
     * @return the foreground colour
     */
    public NamedColor getForegroundColour() {
        return foregroundColour;
    }

    @Override
    public String getValueAsString() {
        return foregroundColour.toString();
    }
}
