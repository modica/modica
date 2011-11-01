package org.afpparser.parser;

import org.afpparser.afp.modca.SfIntroducer;

public interface AfpHandler {
    void handleBegin(SfIntroducer sf);

    void handleEnd(SfIntroducer sf);

    void handle(SfIntroducer sf);

    public static AfpHandler DEFAULT = new AfpHandler() {
        public void handle(SfIntroducer sf) {
        }

        public void handleBegin(SfIntroducer sf) {
        }

        public void handleEnd(SfIntroducer sf) {
        }
    };
}
