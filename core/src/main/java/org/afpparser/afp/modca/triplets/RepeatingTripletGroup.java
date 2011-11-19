package org.afpparser.afp.modca.triplets;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class RepeatingTripletGroup implements Iterable<List<Triplet>> {
    private final List<List<Triplet>> repeatingGroup;
    private final RepeatingGroupIterator rgIterator;

    RepeatingTripletGroup(List<List<Triplet>> repeatingGroup) {
        this.repeatingGroup = repeatingGroup;
        rgIterator = new RepeatingGroupIterator();
    }

    public int getSize() {
        return repeatingGroup.size();
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
