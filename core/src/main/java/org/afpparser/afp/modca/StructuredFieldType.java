package org.afpparser.afp.modca;

import org.afpparser.afp.modca.SFTypeFactory.TypeCodes;

public interface StructuredFieldType {
    byte[] getId();

    TypeCodes getTypeCode();

    String getName();
}
