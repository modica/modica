package org.afpparser.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
import org.afpparser.afp.modca.triplets.TripletHandler;
import org.afpparser.common.ByteUtils;
import org.junit.Test;

/**
 * Test case for subclasses of {@link StructuredFieldWithTripletGroup}.
 *
 * @param <T> subclass of {@link StructuredFieldWithTripletGroup}
 */
public abstract class StructuredFieldWithTripletGroupTestCase<T extends StructuredFieldWithTripletGroup>
        extends StructuredFieldTestCase<T> {
    private StructuredFieldWithTripletGroup sut;
    private RepeatingTripletGroup rGroup;

    public void setMembers(StructuredFieldWithTripletGroup sut, SfIntroducer intro,
            RepeatingTripletGroup rGroup) {
        super.setMemebers(sut, intro);
        this.sut = sut;
        this.rGroup = rGroup;
    }

    @Override
    @Test
    public void testGetters() {
        assertEquals(rGroup, sut.getTripletGroup());
        assertEquals(rGroup.size() > 0, sut.hasTripletGroup());
    }

    public static RepeatingTripletGroup createGenericRepeatingGroup() throws MalformedURLException,
            UnsupportedEncodingException {
        // I think this works... will find out soon enough
        byte[] data = ByteUtils.hexToBytes("220C028600C3F0C8F2F0F0C2F00C028500E3F1E5F1F0F5F0F0042600000424050200");
        return TripletHandler.parseRepeatingGroup(data);
    }
}
