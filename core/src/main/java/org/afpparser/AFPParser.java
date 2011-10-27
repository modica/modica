package org.afpparser;

import java.io.File;
import java.io.IOException;

import org.afpparser.afp.modca.StructuredField;
import org.afpparser.parser.DocumentReader;

public class AFPParser {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("This takes a single parameter which is the AFP document");
            return;
        }

        File afpDoc = new File(args[0]);
        if (!afpDoc.exists()) {
            System.out.println("The AFP document does not exist");
            return;
        }
        try {
            DocumentReader docReader = new DocumentReader(afpDoc);
            for (StructuredField sf : docReader) {
                System.out.println(sf);   
            }
        } catch (IOException e) {
            System.out.println("IO error occurred");
        }
    }
}
