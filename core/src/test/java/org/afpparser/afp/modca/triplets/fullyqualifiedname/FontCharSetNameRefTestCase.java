package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.triplets.AbstractTripletTest;
import org.junit.Before;
import org.junit.Test;

public class FontCharSetNameRefTestCase extends AbstractTripletTest<FontCharSetNameRef>{
    private FontCharSetNameRef x;
    private FontCharSetNameRef y;
    private FontCharSetNameRef z;
    private FontCharSetNameRef notEqual;
    @Before
    @Override
    public void setUp() {
        x = new FontCharSetNameRef(1, "a");
        y = new FontCharSetNameRef(1, "a");
        z = new FontCharSetNameRef(1, "a");
        notEqual = new FontCharSetNameRef(1, "b");
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetters() {
       assertEquals("a", x.getString());
       String expectedString = "this is test string";
       FontCharSetNameRef sut = new FontCharSetNameRef(0, expectedString);
       assertEquals(expectedString, sut.getString());
    }
}
