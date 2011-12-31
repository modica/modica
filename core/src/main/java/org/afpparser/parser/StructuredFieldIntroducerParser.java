package org.afpparser.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * This class reads from a fileInputStream, identifying structured field
 * introducers, creating the light-weight {@link StructuredFieldIntroducer} and delegating
 * further processing to a {@link StructuredFieldIntroducerHandler}.
 * 
 */
public final class StructuredFieldIntroducerParser {

    private final StructuredFieldIntroducerReader reader;

    private final StructuredFieldIntroducerHandler handler;

    /**
     * 
     * @param afpFileInputStream
     *            An AFP file stream.
     * @param handler
     *            An SFIntroducerHandler to handle {@link StructuredFieldIntroducer} parsing
     *            events.
     * @throws FileNotFoundException
     *             Thrown if the AFP file is invalid.
     */
    public StructuredFieldIntroducerParser(FileInputStream afpFileInputStream, StructuredFieldIntroducerHandler handler)
            throws FileNotFoundException {
        this.reader = new StructuredFieldIntroducerReader(afpFileInputStream);
        this.handler = handler;
    }

    /**
     * Iterates through the StructruredFieldIntroducers parsed by the reader
     * and dispatches them to the handler.
     * 
     * @throws IOException
     */
    public void parse() throws IOException {
        handler.startAfp();
        for (StructuredFieldIntroducer sf : reader) {
            switch (sf.getType().getTypeCode()) {
            case Begin:
                handler.handleBegin(sf);
                break;
            case End:
                handler.handleEnd(sf);
                break;
            default:
                handler.handle(sf);
            }
        }
        handler.endAfp();
    }
}
