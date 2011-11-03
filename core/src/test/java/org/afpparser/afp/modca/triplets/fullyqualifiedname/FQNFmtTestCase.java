package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link FQNFmt}.
 */
public class FQNFmtTestCase {

    @Test
    public void testGetId() {
        assertEquals(0x00, FQNFmt.character_string.getId());
        assertEquals(0x10, FQNFmt.oid.getId());
        assertEquals(0x20, FQNFmt.url.getId());
    }
}
