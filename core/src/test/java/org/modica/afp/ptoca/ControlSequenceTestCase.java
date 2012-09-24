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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * An abstract test that is the super class of all {@link ControlSequence} tests.
 */
public abstract class ControlSequenceTestCase<C extends ControlSequence> {

    private C sut;
    private ControlSequenceIdentifier expectedCsId;
    private boolean isChained;
    private int length;

    public void setMembers(C sut, ControlSequenceIdentifier expectedCsId, boolean isChained,
            int length) {
        this.sut = sut;
        this.expectedCsId = expectedCsId;
        this.isChained = isChained;
        this.length = length;
    }

    @Test
    public void testControlSequenceMethods() {
        assertEquals(expectedCsId, sut.getControlSequenceIdentifier());
        assertEquals(isChained, sut.isChained());
        assertEquals(length, sut.getLength());
    }

    @Test
    public void testGetFraction() {
        assertEquals(0.5, sut.getFraction((byte) 0x80), 0.000001);
        assertEquals(0.25, sut.getFraction((byte) 0x40), 0.000001);
        assertEquals(0.125, sut.getFraction((byte) 0x20), 0.000001);
        assertEquals(0.0625, sut.getFraction((byte) 0x10), 0.000001);
        assertEquals(0.03125, sut.getFraction((byte) 0x08), 0.000001);
        assertEquals(0.015625, sut.getFraction((byte) 0x04), 0.000001);
        assertEquals(0.0078124, sut.getFraction((byte) 0x02), 0.000001);
        assertEquals(0.00390625, sut.getFraction((byte) 0x01), 0.000001);

        // Test mixtures
        assertEquals(0.75, sut.getFraction((byte) 0xC0), 0.000001);
        assertEquals(0.01171875, sut.getFraction((byte) 0x03), 0.000001);
        assertEquals(0.09375, sut.getFraction((byte) 0x18), 0.000001);
        assertEquals(0.99609365, sut.getFraction((byte) 0xFF), 0.000001);
    }
}
