package org.afpparser;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        CommandLineParser cliParser = new BasicParser();

        Options opts = createCliOptions();

        CommandLine cmd = cliParser.parse(opts, args);
        if (cmd.hasOption('p')) {
            AFPParser.main(cmd.getOptionValue('p'));
        } else if (cmd.hasOption('h')) {
            System.out.println("here");
        }
    }

    public static Options createCliOptions() {
        final Options opts = new Options();
        opts.addOption("h", "help", false, "Print usage information");
        OptionGroup optGroup = new OptionGroup();
        optGroup.addOption(new Option("p", "parse", true, "Parse an AFP document"));
        optGroup.addOption(new Option("c", "compare", true, "Compare two AFP documents"));
        opts.addOptionGroup(optGroup);
        return opts;
    }
}
