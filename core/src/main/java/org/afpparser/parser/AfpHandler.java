package org.afpparser.parser;

import org.afpparser.afp.modca.SFIntroducer;

public interface AfpHandler {
    void handleBegin(SFIntroducer sf);

    void handleEnd(SFIntroducer sf);

    void handle(SFIntroducer sf);

    public static AfpHandler DEFAULT = new AfpHandler() {
        public void handle(SFIntroducer sf) {
        }

        public void handleBegin(SFIntroducer sf) {
        }

        public void handleEnd(SFIntroducer sf) {
        }
    };

}
