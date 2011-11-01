package org.afpparser.afp.modca;

public interface StructuredField {
    boolean hasExtData();

    boolean hasSegmentedData();

    boolean hasDataPadding();

    int getLength();

    SFType getType();

    long getOffset();

    int getExtLength();

    int bytesToNextStructuredField();

}
