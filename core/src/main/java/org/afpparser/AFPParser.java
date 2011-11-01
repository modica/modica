package org.afpparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.afpparser.afp.modca.SfIntroducer;
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
        for (SfIntroducer sf : documentReader) {
            switch (sf.getType().getTypeCode()) {
            case Begin:
                afpHandler.handleBegin(sf);
                break;
            case End:
                afpHandler.handleEnd(sf);
                break;
            default:
                afpHandler.handle(sf);
            }
        }
    }

    public static void main(String arg) {
        File afpDoc = new File(arg);
        if (!afpDoc.isFile()) {
            System.out.println("The AFP document does not exist");
            return;
        }

        try {
            AfpHandler sfDumper = new AfpHandler() {
                private String indent = "";
                public void handle(SfIntroducer sf) {
                    System.out.println(indent + sf);
                }

                public void handleBegin(SfIntroducer sf) {
                    System.out.println(indent + sf);
                    indent += "  ";
                }

                public void handleEnd(SfIntroducer sf) {
                    indent = indent.substring(0, indent.length() - 2);
                    System.out.println(indent + sf);
                }
            };
            new AFPParser(afpDoc, sfDumper).parse();
        } catch (IOException e) {
            System.out.println("IO error occurred");
        }
    }
}
