package org.afpparser.parser;

import java.io.PrintStream;

import org.afpparser.afp.modca.StructuredField;

/**
 * This class is used for printing StructuredFieldHandler events to a
 * PrintStream.
 * 
 */
public class PrintingSFHandler implements StructuredFieldHandler {

    private final PrintStream out;

    private PrintingSFHandler() {
        this(System.out);
    }

    private PrintingSFHandler(PrintStream out) {
        this.out = out;
    }

    /**
     * Creates a new instance of PrintingSFHandler.
     * 
     * @param out
     *            PrintStream to print to.
     * @return
     */
    public static StructuredFieldHandler newInstance(PrintStream out) {
        return new PrintingSFHandler(out);
    }

    /**
     * Create a new instance of PrintingSFHandler that prints to stdout.
     * 
     * @return
     */
    public static StructuredFieldHandler newInstance() {
        return newInstance(System.out);
    }

    @Override
    public void handle(StructuredField structuredField) {
        out.println(structuredField);
    }
}
