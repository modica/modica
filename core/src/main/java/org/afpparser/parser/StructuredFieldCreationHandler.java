package org.afpparser.parser;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.StructuredField;

public class StructuredFieldCreationHandler implements StructuredFieldHandler {

    private final List<StructuredField> structuredFields;

    public StructuredFieldCreationHandler() {
        structuredFields = new ArrayList<StructuredField>();
    }

    @Override
    public void handle(StructuredField structuredField) {
        System.out.println(structuredField);
        structuredFields.add(structuredField);

    }

}
