package org.afpparser.afp.ptoca;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Parameters;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link NoOperation}.
 */
public class NoOperationTestCase extends ControlSequenceTestCase<NoOperation> {
    private NoOperation sut;
    private final String comment = "This is a test comment.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        Parameters params = new Parameters(comment.getBytes("Cp500"), "Cp500");
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.NO_OPERATION;
        int length = 25;
        boolean isChained = true;

        sut = new NoOperation(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(comment, sut.getCommentAsString("Cp500"));

        assertEquals(comment, sut.getValueAsString());
    }
}
