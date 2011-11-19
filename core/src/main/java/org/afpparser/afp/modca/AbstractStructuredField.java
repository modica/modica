package org.afpparser.afp.modca;


/**
 * A class that handles all the methods common to all structured fields.
 */
public abstract class AbstractStructuredField implements StructuredField {

    private final SfIntroducer introducer;

    public AbstractStructuredField(SfIntroducer introducer) {
        this.introducer = introducer;
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
}
