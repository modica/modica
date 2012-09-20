package org.modica.afp.modca.structuredfields;


/**
 * A class that handles all the methods common to all structured fields.
 */
public abstract class AbstractStructuredField implements StructuredField {

    private final StructuredFieldIntroducer introducer;

    public AbstractStructuredField(StructuredFieldIntroducer introducer) {
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
    public StructuredFieldType getType() {
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
    public String toString() {
        return getType().getName();
    }
}
