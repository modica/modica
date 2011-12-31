package org.afpparser.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.afpparser.afp.modca.StructuredFieldFactory;
import org.afpparser.afp.modca.StructuredFieldFactoryImpl;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.model.builder.ModelBuildingSFHandler;

/**
 * This class is performs a full parse of an AFP document producing the object model.  
 * Parsing and creation events are also published to the  {@link AfpHandler} constructor parameters.
 *
 */
public class AfpParser {

    private final StructuredFieldIntroducerParser parser;
    
    public AfpParser(FileInputStream afpFileInputStream, AfpHandler... handlers)
            throws FileNotFoundException {

        StructuredFieldHandler structuredFieldHandler = StructuredFieldHandlers.aggregate(
                handlers);
       
        StructuredFieldFactory structuredFieldFactory = new StructuredFieldFactoryImpl(
                afpFileInputStream.getChannel());
        
        StructuredFieldIntroducerHandler structuredFieldCreator = new StructuredFieldCreator(
                structuredFieldFactory, structuredFieldHandler);
        
        StructuredFieldIntroducerHandler sFIntroducerHandler = StructuredFieldIntroducerHandlers.aggregate(
                handlers, structuredFieldCreator);
        
        parser = new StructuredFieldIntroducerParser(afpFileInputStream, sFIntroducerHandler);
    }
    
    /**
     * Parses the AFP document.
     * @throws IOException
     */
    public void parse() throws IOException {
        parser.parse();
    }
}
