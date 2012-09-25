package org.modica.afp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.StructuredFieldHandler;

/**
 * This class constructs an object model.
 */
public class ModelBuildingSFHandler implements StructuredFieldHandler {

    private final List<StructuredField> structuredFields;

    public ModelBuildingSFHandler() {
        structuredFields = new ArrayList<StructuredField>();
    }

    @Override
    public void startAfp() {
    }

    @Override
    public void handleBegin(StructuredField structuredField) {
        add(structuredField);
    }

    @Override
    public void handleEnd(StructuredField structuredField) {
        add(structuredField);
    }

    @Override
    public void handle(StructuredField structuredField) {
        add(structuredField);
    }

    @Override
    public void endAfp() {
    }

    private void add(StructuredField structuredField) {
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
