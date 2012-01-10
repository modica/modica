package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
