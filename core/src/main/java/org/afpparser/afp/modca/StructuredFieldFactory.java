package org.afpparser.afp.modca;


public interface StructuredFieldFactory {
    
    StructuredField createBegin(SfIntroducer introducer, byte[] payload);
    
    StructuredField createMap(SfIntroducer introducer, byte[] payload);
}
