package org.afpparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.afpparser.parser.AFPDocumentParser;
import org.afpparser.parser.AfpHandlers;
import org.afpparser.parser.PrintingSFHandler;
import org.afpparser.parser.PrintingSFIntoducerHandler;
import org.afpparser.parser.SimpleDocumentParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) throws IOException {
        CommandLineParser cliParser = new BasicParser();
        Options opts = createCliOptions();
        FileInputStream inStream = null;
        try {
            CommandLine cmd = cliParser.parse(opts, args);
            if (cmd.hasOption('p')) {
                File afpDoc = new File(cmd.getOptionValue('p'));
                if (!afpDoc.isFile()) {
                    System.out.println("The AFP document does not exist");
                    return;
                }
                inStream = new FileInputStream(afpDoc);
                new AFPDocumentParser(inStream, PrintingSFIntoducerHandler.newInstance()).parse();
            } else if (cmd.hasOption('f')) {
                File afpDoc = new File(cmd.getOptionValue('f'));
                if (!afpDoc.isFile()) {
                    System.out.println("The AFP document does not exist");
                    return;
                }

                inStream = new FileInputStream(afpDoc);

                SimpleDocumentParser parser = new SimpleDocumentParser(inStream,
                       AfpHandlers.delegateTo(
                                PrintingSFIntoducerHandler.newInstance(),
                                PrintingSFHandler.newInstance()
                        )
                );

                parser.parse();

                // List<StructuredField> objectModel = parser.getObjectModel();

            } else {
                printHelp(opts);
            }
        } catch (IOException ioe) {
            System.out.println("IO exception: " + ioe.getMessage());
        } catch (ParseException pe) {
            System.out.println("Unexpected exception: " + pe.getMessage());
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
    }

    private static void printHelp(Options opts) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("afpparser", opts);
    }

    private static Options createCliOptions() {
        final Options opts = new Options();
        opts.addOption("h", "help", false, "Print usage information");
        OptionGroup optGroup = new OptionGroup();
        optGroup.addOption(new Option("p", "parse", true,
                "Parse an AFP document and print the structured field data to the commandline."));
        optGroup.addOption(new Option("c", "compare", true, "Compare two AFP documents and print"
                + " any differences to the command line."));
        optGroup.addOption(new Option("f", "full-parse", true, "Parse the AFP document and create"
                + " a richer object model."));
        opts.addOptionGroup(optGroup);
        return opts;
    }
}
