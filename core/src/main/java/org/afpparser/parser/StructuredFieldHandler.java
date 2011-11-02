package org.afpparser.parser;

import org.afpparser.afp.modca.SfIntroducer;

public interface StructuredFieldHandler {
    void handleBegin(SfIntroducer sf);

    void handleEnd(SfIntroducer sf);

    void handle(SfIntroducer sf);
}
