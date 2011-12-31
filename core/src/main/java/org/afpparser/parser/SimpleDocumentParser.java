package org.afpparser.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.afpparser.afp.modca.StructuredFieldFactory;
import org.afpparser.afp.modca.StructuredFieldFactoryImpl;
import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 * This class is performs a full parse of an AFP document producing the object model.  
 * Parsing and creation events are also published to the  {@link AfpHandler} constructor parameters.
 *
 */
public class SimpleDocumentParser {

    private final AFPDocumentParser parser;
    
    private final ObjectModelCreator objectModelCreatingHandler;
    
    public SimpleDocumentParser(FileInputStream afpFile, AfpHandler... handlers)
            throws FileNotFoundException {
        objectModelCreatingHandler = new ObjectModelCreator();
        
        StructuredFieldHandler structuredFieldHandler = StructuredFieldHandlers.aggregate(
                handlers, objectModelCreatingHandler);
       
        StructuredFieldFactory structuredFieldFactory = new StructuredFieldFactoryImpl(
                afpFile.getChannel());
        
        StructuredFieldIntroducerHandler structuredFieldCreator = new StructuredFieldCreator(
                structuredFieldFactory, structuredFieldHandler);
        
        StructuredFieldIntroducerHandler sFIntroducerHandler = StructuredFieldIntroducerHandlers.aggregate(
                handlers, structuredFieldCreator);
        
        parser = new AFPDocumentParser(afpFile, sFIntroducerHandler);
    }
    
    /**
     * Parses the AFP document.
     * @throws IOException
     */
    public void parse() throws IOException {
        parser.parse();
    }
    
    /**
     * Returns the object model.  The client code must call parse first.
     * 
     * @return
     */
    public List<StructuredField> getObjectModel() {
        return objectModelCreatingHandler.getObjectModel();
    }
    
}
