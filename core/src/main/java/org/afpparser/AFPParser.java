package org.afpparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.afpparser.afp.modca.StructuredField;
import org.afpparser.parser.AfpHandler;
import org.afpparser.parser.DocumentReader;

public class AFPParser {

    private final DocumentReader documentReader;

    private final AfpHandler afpHandler;

    public AFPParser(File afpFile) throws FileNotFoundException {
        this(afpFile, AfpHandler.DEFAULT);
    }

    public AFPParser(File afpFile, AfpHandler afpHandler) throws FileNotFoundException {
        this.documentReader = new DocumentReader(afpFile);
        this.afpHandler = afpHandler;
    }

    public void parse() throws IOException {
        for (StructuredField sf : documentReader) {
            afpHandler.handle(sf);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("This takes a single parameter which is the AFP document");
            return;
        }

        File afpDoc = new File(args[1]);
        if (!afpDoc.isFile()) {
            System.out.println("The AFP document does not exist");
            return;
        }

        try {
            AfpHandler sfDumper = new AfpHandler() {
                @Override
                public void handle(StructuredField sf) {
                    System.out.println(sf);
                }
            };
            new AFPParser(afpDoc, sfDumper).parse();
        } catch (IOException e) {
            System.out.println("IO error occurred");
        }
    }
}
