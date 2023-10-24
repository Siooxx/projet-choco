package base;

import base.options.OptionsChecker;
import base.options.OptionsConfig;
import base.utils.OptionHandler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    // Timeout de 5 minutes
    public static long timeout = 300000;
    // Booléan pour afficher ou non toutes les solutions
    public static boolean allSolutions = false;

    public static void main(String[] args) throws ParseException {

        // Configuration des options pour la ligne de commande
        final Options options = OptionsConfig.configParameters();
        // Initialisation du parseur pour analyser les options de la ligne de commande
        final CommandLineParser parser = new DefaultParser();
        // Analyse des arguments fournis en ligne de commande
        final CommandLine line = parser.parse(options, args);

        // Si l'option d'aide est présente ou si aucun argument n'est fourni, affiche le message d'aide
        if (line.hasOption("help") || args.length == 0) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Options possibles :", options, true);
            System.exit(0);
        }

        // Création de l'instance OptionChecker avec des valeurs initiales
        OptionsChecker optionChecker = new OptionsChecker(timeout, false);

        // Vérifie les arguments et les options
        for (Option opt : line.getOptions()) optionChecker.checkOption(line, opt.getLongOpt());

        // Récupération des deux variables
        timeout = optionChecker.getTimeout();
        allSolutions = optionChecker.getAllSolutions();

        // Gestion et Affichage
        new OptionHandler(line).handleOptions();
    }
}