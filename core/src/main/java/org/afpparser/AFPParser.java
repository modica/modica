package org.afpparser;

import java.io.File;

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

    }
}
