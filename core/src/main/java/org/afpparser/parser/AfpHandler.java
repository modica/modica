package org.afpparser.parser;

import org.afpparser.afp.modca.StructuredField;

public interface AfpHandler {
    void handle(StructuredField sf);

    public static AfpHandler DEFAULT = new AfpHandler() {
        public void handle(StructuredField sf) {
        }
    };

}
