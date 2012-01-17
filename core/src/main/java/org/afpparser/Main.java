package org.afpparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.model.builder.ModelBuildingSFHandler;
import org.afpparser.parser.AfpParser;
import org.afpparser.parser.PrintingSFHandler;
import org.afpparser.parser.PrintingSFIntroducerHandler;
import org.afpparser.parser.StructuredFieldIntroducerParser;

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
                new StructuredFieldIntroducerParser(inStream, new PrintingSFIntroducerHandler(System.out)).parse();
            } else if (cmd.hasOption('f')) {
                File afpDoc = new File(cmd.getOptionValue('f'));
                if (!afpDoc.isFile()) {
                    System.out.println("The AFP document does not exist");
                    return;
                }

                inStream = new FileInputStream(afpDoc);
                ModelBuildingSFHandler modelBuilder = new ModelBuildingSFHandler();
                AfpParser.forInput(inStream)
                        .withHandler(new PrintingSFIntroducerHandler(System.out))
                        .withHandler(new PrintingSFHandler(System.out))
                        .withHandler(modelBuilder)
                        .parse();
                List<StructuredField> model = modelBuilder.getObjectModel();
                System.out.println("This document contains " + model.size() + " structured fields.");

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
