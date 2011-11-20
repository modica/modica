package org.afpparser.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;

import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;
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

    /*public static RepeatingTripletGroup createGenericRepeatingGroup() throws MalformedURLException,
            UnsupportedEncodingException {
        List<List<Triplet>> repeatingTriplets = new ArrayList<List<Triplet>>();
        repeatingTriplets.add(StructuredFieldWithTripletTestCase.addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF));
        return new R
    }*/
}
