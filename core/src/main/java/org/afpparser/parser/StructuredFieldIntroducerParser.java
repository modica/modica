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

    private final Iterable<StructuredFieldIntroducer> introducers;

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
        this(new StructuredFieldIntroducerReader(afpFileInputStream), handler);
    }

    /**
     * 
     * @param introducers
     *            iterable over {@link StructuredFieldIntroducer}.
     * @param handler
     *            An SFIntroducerHandler to handle {@link StructuredFieldIntroducer} parsing
     *            events.
     */
    public StructuredFieldIntroducerParser(Iterable<StructuredFieldIntroducer> introducers, StructuredFieldIntroducerHandler handler) {
        this.introducers = introducers;
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
        for (StructuredFieldIntroducer sf : introducers) {
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
