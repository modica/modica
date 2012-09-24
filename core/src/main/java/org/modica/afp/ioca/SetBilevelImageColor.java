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

package org.modica.afp.ioca;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.NamedColor;

/**
 * Sets the bi-level image colour.
 */
public class SetBilevelImageColor implements SelfDefiningField {
    private static int LENGTH = 5;

    private final byte area;
    private final NamedColor colour;

    public SetBilevelImageColor(Parameters params) {
        byte length = params.getByte();
        assert length == 0x04;
        area = params.getByte();
        assert area == 0x00;
        byte reserved = params.getByte();
        assert reserved == (byte) 0x00;
        colour = NamedColor.getValue(params);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public byte getId() {
        return (byte) 0xF6;
    }

    /**
     * Return the colour.
     *
     * @return the colour
     */
    public NamedColor getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return "SetBilevelImageColor area=" + area + " colour=" + colour;
    }

    @Override
    public String getName() {
        return "SetBilevelImageColor";
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("Colour", colour));
        return params;
    }
}
