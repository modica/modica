package org.modica.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;
import org.modica.afp.modca.triplets.TripletHandler;
import org.modica.common.ByteUtils;

/**
 * Test case for subclasses of {@link StructuredFieldWithTripletGroup}.
 *
 * @param <T> subclass of {@link StructuredFieldWithTripletGroup}
 */
public abstract class StructuredFieldWithTripletGroupTestCase<T extends StructuredFieldWithTripletGroup>
        extends StructuredFieldTestCase<T> {
    private StructuredFieldWithTripletGroup sut;
    private RepeatingTripletGroup rGroup;

    public void setMembers(StructuredFieldWithTripletGroup sut, StructuredFieldIntroducer intro,
            RepeatingTripletGroup rGroup) {
        super.setMembers(sut, intro);
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
        byte[] data = ByteUtils.hexToBytes("0022"
                + "0C028600C3F0C8F2F0F0" + "C2F00C028500E3F1E5F1F0F5F0F00426000004240502");
        Parameters params = new Parameters(data, "Cp500");
        Context context = new ContextImpl();
        return TripletHandler.parseRepeatingGroup(params, context);
    }
}
