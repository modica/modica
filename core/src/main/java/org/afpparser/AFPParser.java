package org.afpparser;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.afpparser.afp.modca.StructuredField;
import org.afpparser.parser.DocumentReader;
import org.afpparser.parser.AfpHandler;

public class AFPParser {
    
    private final DocumentReader documentReader; 

    private final AfpHandler afpHandler;

    public AFPParser(File afpFile) throws FileNotFoundException {
        documentReader = new DocumentReader(afpFile);
        afpHandler = new SfDumper();
    }

    public void parse() throws IOException {
        for (StructuredField sf : documentReader) {
            afpHandler.handle(sf);
        }
    }

    private static class SfDumper implements AfpHandler {
        public void handle(StructuredField sf) {
            System.out.println(sf);            
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("This takes a single parameter which is the AFP document");
            return;
        }

        File afpDoc = new File(args[0]);
        if (!afpDoc.isFile()) {
            System.out.println("The AFP document does not exist");
            return;
        }

        try {
            new AFPParser(afpDoc).parse();
        } catch (IOException e) {
            System.out.println("IO error occurred");
        }
    }
}
