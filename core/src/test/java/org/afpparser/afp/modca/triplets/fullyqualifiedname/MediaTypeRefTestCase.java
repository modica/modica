package org.afpparser.afp.modca.triplets.fullyqualifiedname;

import org.junit.Before;

public class MediaTypeRefTestCase extends FQNCharStringDataTestCase<MediaTypeRef> {

    @Before
    @Override
    public void setUp() {
        String expectedString = "expected character string";
        int length = 1;
        MediaTypeRef x = new MediaTypeRef(length, expectedString);
        MediaTypeRef y = new MediaTypeRef(length, expectedString);
        MediaTypeRef z = new MediaTypeRef(length, expectedString);
        MediaTypeRef notEqual = new MediaTypeRef(1, "not expected");
        FQNType type = FQNType.media_type_ref;
        setXYZandTypes(x, y, z, notEqual, length, type, expectedString);
    }
}