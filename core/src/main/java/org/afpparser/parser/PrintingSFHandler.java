package org.afpparser.parser;

import java.io.PrintStream;

import org.afpparser.afp.modca.StructuredField;

public class PrintingSFHandler implements StructuredFieldHandler {

    private final PrintStream out;

    private PrintingSFHandler() {
        this(System.out);
    }

    private PrintingSFHandler(PrintStream out) {
        this.out = out;
    }

    public static StructuredFieldHandler newInstance(PrintStream out) {
        return new PrintingSFHandler(out);
    }

    public static StructuredFieldHandler newInstance() {
        return newInstance(System.out);
    }

    @Override
    public void handle(StructuredField structuredField) {
        out.println(structuredField);
    }
}
