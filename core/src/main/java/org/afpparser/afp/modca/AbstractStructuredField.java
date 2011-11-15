package org.afpparser.afp.modca;

import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.triplets.Triplet;

/**
 * A class that handles all the mothods common to all structured fields. 
 */
public abstract class AbstractStructuredField implements StructuredField {

    private final SfIntroducer introducer;
    private final List<Triplet> triplets;

    public AbstractStructuredField(SfIntroducer introducer, List<Triplet> triplets) {
        this.introducer = introducer;
        this.triplets = triplets;
    }

    @Override
    public boolean hasExtData() {
        return introducer.hasExtData();
    }

    @Override
    public boolean hasSegmentedData() {
        return introducer.hasSegmentedData();
    }

    @Override
    public boolean hasDataPadding() {
        return introducer.hasDataPadding();
    }

    @Override
    public int getLength() {
        return introducer.getLength();
    }

    @Override
    public SfType getType() {
        return introducer.getType();
    }

    @Override
    public long getOffset() {
        return introducer.getOffset();
    }

    @Override
    public int getExtLength() {
        return introducer.getExtLength();
    }

    @Override
    public int bytesToNextStructuredField() {
        return introducer.bytesToNextStructuredField();
    }

    @Override
    public boolean hasTriplets() {
        return triplets != null && triplets.size() > 0;
    }

    @Override
    public List<Triplet> getTriplets() {
        return Collections.unmodifiableList(triplets);
    }
}
