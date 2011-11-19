package org.afpparser.afp.modca.triplets;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A list of triplets can be grouped into repeating groups, such that there are essentially multiple
 * groups of triplets ({@link List<{@link List<{@link Triplet}>}>}). This object implements
 * {@link Iterable} to iterate through each group of triplets.
 */
public final class RepeatingTripletGroup implements Iterable<List<Triplet>> {

    private final List<List<Triplet>> repeatingGroup;

    private final RepeatingGroupIterator rgIterator;

    private final int tripletCount;

    RepeatingTripletGroup(List<List<Triplet>> repeatingGroup) {
        this.repeatingGroup = repeatingGroup;
        rgIterator = new RepeatingGroupIterator();
        int numOfTriplets = 0;
        for (List<Triplet> tripletList : repeatingGroup) {
            numOfTriplets += tripletList.size();
        }
        tripletCount = numOfTriplets;
    }

    /**
     * Returns the number of triplet groups.
     *
     * @return the number of triplet groups
     */
    public int size() {
        return repeatingGroup.size();
    }

    /**
     * Returns the total number of triplets in all the groups.
     *
     * @return a total number of triplets
     */
    public int totalSize() {
        return tripletCount;
    }

    @Override
    public Iterator<List<Triplet>> iterator() {
        return rgIterator;
    }

    private final class RepeatingGroupIterator implements Iterator<List<Triplet>> {

        private final Iterator<List<Triplet>> repeatingGroupIterator = repeatingGroup.iterator();

        @Override
        public boolean hasNext() {
            return repeatingGroupIterator.hasNext();
        }

        @Override
        public List<Triplet> next() {
            return Collections.unmodifiableList(repeatingGroupIterator.next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove a triplet group");
        }
    }
}
