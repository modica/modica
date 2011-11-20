package org.afpparser.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 * This class constructs an object model.
 */
public class ObjectModelCreator implements StructuredFieldHandler {

    private final List<StructuredField> structuredFields;

    public ObjectModelCreator() {
        structuredFields = new ArrayList<StructuredField>();
    }

    @Override
    public void handle(StructuredField structuredField) {
        structuredFields.add(structuredField);
    }

    /**
     * Gets the object model.
     * 
     * @return
     */
    public List<StructuredField> getObjectModel() {
        return Collections.unmodifiableList(structuredFields);
    }
}
