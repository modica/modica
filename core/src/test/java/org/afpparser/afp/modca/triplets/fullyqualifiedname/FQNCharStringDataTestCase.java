package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.triplets.AbstractTripletTest;
import org.afpparser.afp.modca.triplets.TripletIdentifiers;
import org.junit.Test;

public abstract class FQNCharStringDataTestCase<T extends FQNCharStringData>
        extends AbstractTripletTest<T> {
    private T x;
    private FQNType type;
    private String expectedString;
    private int length;
    
    public void setXYZandTypes(T x, T y, T z, T notEqual, int length, FQNType type,
            String expectedString) {
        this.x = x;
        this.type = type;
        this.expectedString = expectedString;
        this.length = length;
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetters() {
        assertEquals(expectedString, x.getString());
        assertEquals(type, x.getFQNType());
        assertEquals(FQNFmt.character_string, x.getFormat());
        assertEquals(length, x.getLength());
        assertEquals(TripletIdentifiers.fully_qualified_name, x.getTid());
    }
}
