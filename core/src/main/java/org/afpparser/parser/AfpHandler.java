package org.afpparser.parser;

import org.afpparser.afp.modca.SFIntroducer;

public interface AfpHandler {
    void handle(SFIntroducer sf);

    public static AfpHandler DEFAULT = new AfpHandler() {
        public void handle(SFIntroducer sf) {
        }
    };

}
