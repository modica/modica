package org.afpparser.afp.modca;

/**
 * A class that handles all the mothods common to all structured fields. 
 */
public abstract class AbstractStructuredField implements StructuredField {

    private final SfIntroducer introducer;

    public AbstractStructuredField(SfIntroducer introducer) {
        this.introducer = introducer;
    }

    /** {@inheritDoc} */
    public boolean hasExtData() {
        return introducer.hasExtData();
    }

    /** {@inheritDoc} */
    public boolean hasSegmentedData() {
        return introducer.hasSegmentedData();
    }

    /** {@inheritDoc} */
    public boolean hasDataPadding() {
        return introducer.hasDataPadding();
    }

    /** {@inheritDoc} */
    public int getLength() {
        return introducer.getLength();
    }

    /** {@inheritDoc} */
    public SfType getType() {
        return introducer.getType();
    }

    /** {@inheritDoc} */
    public long getOffset() {
        return introducer.getOffset();
    }

    /** {@inheritDoc} */
    public int getExtLength() {
        return introducer.getExtLength();
    }

    /** {@inheritDoc} */
    public int bytesToNextStructuredField() {
        return introducer.bytesToNextStructuredField();
    }

}
