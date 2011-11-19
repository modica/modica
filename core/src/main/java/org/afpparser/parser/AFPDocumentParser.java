package org.afpparser.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.afpparser.afp.modca.SfIntroducer;

public class AFPDocumentParser {

    private final DocumentReader documentReader;

    private final SFIntroducerHandler afpHandler;

    public AFPDocumentParser(FileInputStream afpFile, SFIntroducerHandler afpHandler)
            throws FileNotFoundException {
        this.documentReader = new DocumentReader(afpFile);
        this.afpHandler = afpHandler;
    }

    public void parse() throws IOException {
        afpHandler.startAfp();
        for (SfIntroducer sf : documentReader) {
            switch (sf.getType().getTypeCode()) {
            case Begin:
                afpHandler.handleBegin(sf);
                break;
            case End:
                afpHandler.handleEnd(sf);
                break;
            default:
                afpHandler.handle(sf);
            }
        }
        afpHandler.endAfp();
    }
}
