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

package org.modica.afp.modca.triplets;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.Rotation;


/**
 * The Character Rotation triplet is used to specify character rotation relative to the character
 * coordinate system. See the Font Object Content Architecture Reference for further information.
 */
public class CharacterRotation extends Triplet {

    private final static int LENGTH = 4;

    private final Rotation rotation;

    public CharacterRotation(Parameters params) {
        this.rotation = Rotation.getValue(params.getByte());
        params.skip(1);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.character_rotation;
    }

    /**
     * Returns an enumerated type for the character rotation.
     *
     * @return the character rotation
     */
    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public String toString() {
        return "character rotation=" + rotation + " degrees";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CharacterRotation)) {
            return false;
        }
        CharacterRotation charRot = (CharacterRotation) o;
        return this.rotation == charRot.rotation;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + rotation.hashCode();
        hashCode = 31 * hashCode + getTid().hashCode();
        return hashCode;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("Rotation", rotation));
        return params;
    }
}
