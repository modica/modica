package org.afpparser.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

/**
 * Test case for {@link StructuredFieldWithTripletGroup}.
 */
public abstract class StructuredFieldTestCase<S extends AbstractStructuredField> {

    private AbstractStructuredField sut;
    private StructuredFieldIntroducer intro;

    public void setMembers(AbstractStructuredField sut, StructuredFieldIntroducer intro) {
        this.sut = sut;
        this.intro = intro;
    }

    @Test
    public void testGetters() {
        assertEquals(intro.hasExtData(), sut.hasExtData());
        assertEquals(intro.hasSegmentedData(), sut.hasSegmentedData());
        assertEquals(intro.hasDataPadding(), sut.hasDataPadding());
        assertEquals(intro.getLength(), sut.getLength());
        assertEquals(intro.getType(), sut.getType());
        assertEquals(intro.getOffset(), sut.getOffset());
        assertEquals(intro.getExtLength(), sut.getExtLength());
    }

    public abstract void testGetParameters();

    @SuppressWarnings("unchecked")
    protected void testParameters(Map<String, String> expectedParameters, StructuredField sf) {
        Map<String, String> sutParams = sf.getParameters();
        assertEquals(expectedParameters.size(), sutParams.size());
        Entry<String, String>[] sutEntryArray = sutParams.entrySet().toArray(new Entry[0]);
        Entry<String, String>[] expectedEntryArray =
                expectedParameters.entrySet().toArray(new Entry[0]);
        for (int i = 0; i < expectedEntryArray.length; i++) {
            String key = expectedEntryArray[i].getKey();
            assertEquals(key, sutEntryArray[i].getKey());
            assertEquals(key, expectedEntryArray[i].getValue(), sutEntryArray[i].getValue());
        }
    }
}
