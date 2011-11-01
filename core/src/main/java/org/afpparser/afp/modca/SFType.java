package org.afpparser.afp.modca;

public interface SFType {
    byte[] getId();

    TypeCodes getTypeCode();

    String getName();
}
