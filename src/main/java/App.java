import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;

public class App {

    private static long timeout = 300000; // 5 minutes
    private static boolean allSolutions = false;

    public static void main(String[] args) throws ParseException {

        final Options options = configParameters();
        final CommandLineParser parser = new DefaultParser();
        final CommandLine line = parser.parse(options, args);

        boolean helpMode = line.hasOption("help") || args.length == 0;

        if (helpMode) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("cocoAirlines", options, true);
            System.exit(0);
        }

        // Vérifie les arguments et les options
        for (Option opt : line.getOptions())
            checkOption(line, opt.getLongOpt());

        // Affichage en fonction des options
        // Option -c
        if (line.hasOption("with-constraint")) {
            if (line.hasOption("default")){
                System.err.println("L'option -d ne peut être utilisé avec l'option -c !");
                System.exit(0);
            }
            // Option -c et -i (avec possiblement -all)
            else if (line.hasOption("instance"))
                (new GLIAAirlines()).solve(Instance.valueOf(line.getOptionValue("instance")), timeout, allSolutions);
            // Option -c sans -i (avec possiblement -all)
            else {
                for (Instance instance : Instance.values()) {
                    (new GLIAAirlines()).solve(instance, timeout, allSolutions);
                    System.out.println("\n");
                }
            }
        // Option sans -c
        } else {
            // Option -all
            if (line.hasOption("all")) {
                System.err.println("L'option -a doit être utilisé avec l'option -c !");
                System.exit(0);
            }
            // Option sans -c et -all
            else {
                // Option sans -c et -all avec -i
                if (line.hasOption("instance")){
                    final int nb_dividers = Instance.valueOf(line.getOptionValue("instance")).nb_dividers
                            , capacity = Instance.valueOf(line.getOptionValue("instance")).capacity;
                    final int[] exits = Instance.valueOf(line.getOptionValue("instance")).exits;

                    System.out.println("Résultat de l'instance "
                            + line.getOptionValue("instance").charAt(4)
                            + " sans Programmation par Contraintes :");

                    GLIAAirlinesNoConstraints.dividers(nb_dividers, capacity, exits);
                }
                // Option sans -c, -all et -i donc -d
                else {
                    System.out.println("Affichage de toutes les instances sans programmation par contrainte :\n");
                    int i = 0;
                    for (Instance instance : Instance.values()) {
                        System.out.println("Instance " + ++i + " :");
                        GLIAAirlinesNoConstraints.dividers(instance.nb_dividers, instance.capacity, instance.exits);
                        System.out.println("\n");
                    }
                }
            }
        }
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