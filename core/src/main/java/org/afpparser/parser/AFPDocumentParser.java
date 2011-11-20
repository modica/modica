package org.afpparser.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;

/**
 * This class reads from a fileInputStream, identifying structured field
 * introducers, creating the light-weight {@link SfIntroducer} and delegating
 * further processing to a {@link SFIntroducerHandler}.
 * 
 */
public final class AFPDocumentParser {

    private final DocumentReader documentReader;

    private final SFIntroducerHandler afpHandler;

    /**
     * 
     * @param afpFile
     *            An AFP file stream.
     * @param afpHandler
     *            An SFIntroducerHandler to handle {@link SfIntroducer} parsing
     *            events.
     * @throws FileNotFoundException
     *             Thrown if the AFP file is invalid.
     */
    public AFPDocumentParser(FileInputStream afpFile, SFIntroducerHandler afpHandler)
            throws FileNotFoundException {
        this.documentReader = new DocumentReader(afpFile);
        this.afpHandler = afpHandler;
    }

    /**
     * Parse the AFP document.
     * 
     * @throws IOException
     */
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
