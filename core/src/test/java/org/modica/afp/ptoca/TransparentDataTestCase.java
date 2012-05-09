package org.modica.afp.ptoca;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransparentDataTestCase extends ControlSequenceTestCase<TransparentData> {

    private TransparentData sut;
    private final String testString = "This is a string purely used for test purposes.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        Parameters params = new Parameters(testString.getBytes("Cp500"), "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.TRANSPARENT_DATA;
        int length = 49;
        boolean isChained = true;
        Context ctx = mock(Context.class);
        when(ctx.getPTXEncoding()).thenReturn(500);
        sut = new TransparentData(expectedCsId, length, isChained, params, ctx);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(testString, sut.getDataString("Cp500"));
        assertEquals("\"" + testString + "\"", sut.getValueAsString());
    }
}
