package base.options;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

// Création et configuration des options
public class OptionsConfig {

    public static Options configParameters() {
        final Options options = new Options();

        options.addOption(createOption("h", "help", "Affiche le message d'aide"));
        options.addOption(createOptionWithArg("i", "instance", "Instance d'avion (séparateurs, capacité, portes de sortie) - de inst1 à inst9"));
        options.addOption(createOption("a", "all", "Toutes les solutions"));
        options.addOption(createOptionWithArg("t", "timeout", "Définir la limite de temps à la durée spécifiée"));
        options.addOption(createOption("c", "with-constraint", "Exécuter l'instance avec le modèle de contrainte Choco"));
        options.addOption(createOption("d", "default", "Exécuter toutes les instances sans le modèle de contrainte"));

        return options;
    }

    private static Option createOption(String shortOpt, String longOpt, String description) {
        return Option.builder(shortOpt).longOpt(longOpt).desc(description).build();
    }

    private static Option createOptionWithArg(String shortOpt, String longOpt, String description) {
        return Option.builder(shortOpt).longOpt(longOpt).desc(description).hasArg(true).build();
    }
}
