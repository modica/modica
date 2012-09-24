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

import java.util.List;

import org.modica.afp.modca.ParameterAsString;

/**
 * A triplets is a self-identifying parameter that contains three components, the length of the
 * triplet, the ID identifying a triplet and the associated parameters. Triplets are also
 * responsible for parsing and instantiating themselves.
 */
public abstract class Triplet {

    /**
     * Returns the length of this triplet.
     *
     * @return the length of the triplet
     */
    public abstract int getLength();

    /**
     * Returns the triplet identifier.
     *
     * @return the triplet identifier
     */
    public abstract TripletIdentifiers getTid();

    /**
     * The value of the triplet as a String.
     *
     * @return the string representation of this triplet
     */
    public abstract List<ParameterAsString> getParameters();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}
