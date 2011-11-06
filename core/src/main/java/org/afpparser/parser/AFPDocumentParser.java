package org.afpparser.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.afpparser.afp.modca.SfIntroducer;

public class AFPDocumentParser {

    private final DocumentReader documentReader;

    private final StructuredFieldHandler afpHandler;

    public AFPDocumentParser(File afpFile, StructuredFieldHandler afpHandler) throws FileNotFoundException {
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
