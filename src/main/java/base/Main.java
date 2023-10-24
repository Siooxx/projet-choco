package base;

import base.options.OptionsChecker;
import base.utils.OptionHandler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    public static long timeout = 300000; // 5 minutes
    public static boolean allSolutions = false;

    public static void main(String[] args) throws ParseException {

        final Options options = configParameters();
        final CommandLineParser parser = new DefaultParser();
        final CommandLine line = parser.parse(options, args);
        
        if (line.hasOption("help") || args.length == 0) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("cocoAirlines", options, true);
            System.exit(0);
        }

        // Création de l'instance OptionChecker avec des valeurs initiales
        OptionsChecker optionChecker = new OptionsChecker(timeout, false);

        // Vérifie les arguments et les options
        for (Option opt : line.getOptions()) optionChecker.checkOption(line, opt.getLongOpt());

        // Récupération des deux variables
        timeout = optionChecker.getTimeout();
        allSolutions= optionChecker.getAllSolutions();

        // Gestion et Affichage
        new OptionHandler(line).handleOptions();
    }

    // Toutes les options
    private static Options configParameters() {

        final Option helpFileOption = Option
                .builder("h")
                .longOpt("help")
                .desc("Display help message")
                .build();

        final Option instOption = Option
                .builder("i")
                .longOpt("instance")
                .hasArg(true)
                .argName("aircraft instance")
                .desc("aircraft instance (#dividers, capacity, exit doors) - from inst1 to inst9").required(false)
                .build();

        final Option allsolOption = Option
                .builder("a")
                .longOpt("all")
                .hasArg(false)
                .desc("all solutions")
                .required(false)
                .build();

        final Option limitOption = Option
                .builder("t")
                .longOpt("timeout")
                .hasArg(true)
                .argName("timeout in ms")
                .desc("Set the timeout limit to the specified time").required(false).build();

        final Option noConstraintOpt = Option
                .builder("c")
                .longOpt("with-constraint")
                .hasArg(false)
                .desc("Run the instance with the choco constraint model")
                .argName("resolve instance with constraint model")
                .build();

        final Option defaultOption = Option
                .builder("d")
                .longOpt("default")
                .hasArg(false)
                .desc("Run all the instance without the constraint model")
                .argName("resolve instance without constraint model")
                .build();

        // Création de la liste d'option
        final Options options = new Options();
        options.addOption(instOption);
        options.addOption(allsolOption);
        options.addOption(limitOption);
        options.addOption(helpFileOption);
        options.addOption(noConstraintOpt);
        options.addOption(defaultOption);

        return options;
    }

    // Check all parameters values
    public static void checkOption(CommandLine line, String option) {

        switch (option) {
            case "instance":
            case "with-constraint":
            case "default":
                break;
            case "timeout":
                timeout = Long.parseLong(line.getOptionValue(option));
                break;
            case "all":
                allSolutions = true;
                break;
            default: {
                System.err.println("Bad parameter: " + option);
                System.exit(2);
            }
        }
    }
}