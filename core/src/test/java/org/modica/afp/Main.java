package org.modica.afp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.AfpParser;
import org.modica.parser.PrintingSFHandler;
import org.modica.parser.PrintingSFIntroducerHandler;
import org.modica.parser.StructuredFieldIntroducerParser;
import org.modica.serializer.xml.XmlSerializingStructuredFieldHandler;

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
                new StructuredFieldIntroducerParser(inStream,
                        new PrintingSFIntroducerHandler(System.out)).parse();
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
                System.out.println(
                          "This document contains " + model.size() + " structured fields.");
            } else if (cmd.hasOption('x')) {
                File afpDoc = new File(cmd.getOptionValue('x'));
                if (!afpDoc.isFile()) {
                    System.out.println("The AFP document does not exist");
                    return;
                }
                inStream = new FileInputStream(afpDoc);
                AfpParser.forInput(inStream)
                         .withHandler(new XmlSerializingStructuredFieldHandler(System.out))
                         .parse();
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
        helpFormatter.printHelp("MODiCA - An AFP parsing library", opts);
    }

    private static Options createCliOptions() {
        final Options opts = new Options();
        opts.addOption("h", "help", false, "Print usage information");
        OptionGroup optGroup = new OptionGroup();
        optGroup.addOption(new Option("p", "parse", true,
                "Parse an AFP document and print the structured field data to the commandline."));
        optGroup.addOption(new Option("f", "full-parse", true, "Parse the AFP document and create"
                + " a richer object model."));
        optGroup.addOption(new Option("x", "xml-out", true, "Parse the AFP document and prints the"
                + " XML model to stdout."));
        opts.addOptionGroup(optGroup);
        return opts;
    }
}
