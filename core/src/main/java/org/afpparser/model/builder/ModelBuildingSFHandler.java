package org.afpparser.model.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.parser.StructuredFieldHandler;

/**
 * This class constructs an object model.
 */
public class ModelBuildingSFHandler implements StructuredFieldHandler {

    private final List<StructuredField> structuredFields;

    public ModelBuildingSFHandler() {
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
