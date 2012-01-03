package org.afpparser.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.StructuredFieldFactory;
import org.afpparser.afp.modca.StructuredFieldFactoryImpl;

/**
 * This class is performs a full parse of an AFP document producing the object model.
 * Parsing and creation events are also published to the  {@link AfpHandler} constructor parameters.
 *
 */
public class AfpParser {

    private final StructuredFieldIntroducerParser parser;

    private AfpParser(FileInputStream afpFileInputStream,
            StructuredFieldIntroducerHandler[] sfIntroducerHandlers,
            StructuredFieldHandler[] sfHandlers) throws FileNotFoundException {

        StructuredFieldHandler structuredFieldHandler = StructuredFieldHandlers.aggregate(sfHandlers);

        StructuredFieldFactory structuredFieldFactory = new StructuredFieldFactoryImpl(
                afpFileInputStream.getChannel());

        StructuredFieldIntroducerHandler structuredFieldCreator = new StructuredFieldCreator(
                structuredFieldFactory, structuredFieldHandler);

        StructuredFieldIntroducerHandler sFIntroducerHandler = StructuredFieldIntroducerHandlers.aggregate(
                sfIntroducerHandlers, structuredFieldCreator);

        parser = new StructuredFieldIntroducerParser(afpFileInputStream, sFIntroducerHandler);
    }

    public static Builder builder(FileInputStream afpFileInputStream) {
        return new Builder(afpFileInputStream);
    }

    public static class Builder {
        private FileInputStream afpFileInputStream;

        private List<StructuredFieldHandler> sfHandlers = new ArrayList<StructuredFieldHandler>();

        private List<StructuredFieldIntroducerHandler> sfiHandlers = new ArrayList<StructuredFieldIntroducerHandler>();


        private Builder(FileInputStream afpFileInputStream) {
            this.afpFileInputStream = afpFileInputStream;
        }

        public Builder with(StructuredFieldHandler handler) {
            sfHandlers.add(handler);
            return this;
        }

        public Builder with(StructuredFieldIntroducerHandler handler) {
            sfiHandlers.add(handler);
            return this;
        }

        public AfpParser build() throws FileNotFoundException {
            if (sfHandlers.size() == 0) {
                throw new IllegalArgumentException("No StructuredFieldHandler configured");
            }

            return new AfpParser(afpFileInputStream,
                    (StructuredFieldIntroducerHandler[])sfiHandlers.toArray(new StructuredFieldIntroducerHandler[0]),
                    (StructuredFieldHandler[])sfHandlers.toArray(new StructuredFieldHandler[0]));
        }
    }

    /**
     * Parses the AFP document.
     * @throws IOException
     */
    public void parse() throws IOException {
        parser.parse();
    }
}
