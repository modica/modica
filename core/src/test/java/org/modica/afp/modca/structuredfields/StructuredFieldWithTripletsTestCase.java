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

package org.modica.afp.modca.structuredfields;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.ParameterAsStringTestCase;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A test case for {@link StructuredFieldWithTriplets}.
 *
 * @param <T> a sublclass of {@link StructuredFieldWithTriplets}
 */
public abstract class StructuredFieldWithTripletsTestCase<T extends StructuredFieldWithTriplets>
        extends StructuredFieldTestCase<T> {
    private List<Triplet> triplets;
    private T sut;

    public final void setMembers(T sut, StructuredFieldIntroducer intro,
            List<Triplet> triplets) {
        super.setMembers(sut, intro);
        this.triplets = triplets;
        this.sut = sut;
    }

    /**
     * Test the hasTriplets() method depending on the system under test.
     */
    public final void testHasTriplets() {
        assertTrue(sut.hasTriplets());
    };

    public static List<Triplet> addTripletToList(String... tripletStr) throws MalformedURLException,
            UnsupportedEncodingException {
        List<Triplet> triplets = new ArrayList<Triplet>();
        for (String str : tripletStr) {
            triplets.add(FullyQualifiedNameTestCase.createFQN(ByteUtils.hexToBytes(str)));
        }
        return triplets;
    }

    @Test
    public final void testTripletList() {
        assertTrue(triplets.equals(sut.getTriplets()));

        try {
            sut.getTriplets().remove(1);
            fail("This list should be unmodifiable");
        } catch (UnsupportedOperationException e) {
            // Pass
        }
    }

    @Test
    public final void testGetTripletsAsStrings() {
        assertEquals(triplets.size(), sut.getTripletParameters().size());
        for (int i = 0; i < sut.getTripletParameters().size(); i++) {
            List<ParameterAsString> tripletParams = sut.getTripletParameters().get(i);
            ParameterAsStringTestCase.testParameters(tripletParams, triplets.get(i).getParameters());
        }
    }
}
