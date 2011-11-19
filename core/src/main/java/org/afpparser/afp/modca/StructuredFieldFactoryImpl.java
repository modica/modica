package org.afpparser.afp.modca;

import org.afpparser.afp.modca.structuredfields.begin.BeginObjectHandler;
import org.afpparser.afp.modca.structuredfields.map.MapObjectHandler;

public class StructuredFieldFactoryImpl implements StructuredFieldFactory {

    @Override
    public StructuredField createBegin(SfIntroducer introducer, byte[] payload) {
        return BeginObjectHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createMap(SfIntroducer introducer, byte[] payload) {
        return MapObjectHandler.handle(introducer, payload);
    }

}
