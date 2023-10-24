package base.utils;

import base.Main;
import base.core.GLIAAirlines;
import base.enums.Instance;
import org.apache.commons.cli.CommandLine;

// Gère les options de la ligne de commande
public class OptionHandler {

    private final CommandLine line;

    public OptionHandler(CommandLine line) { this.line = line; }

    public void handleOptions() {
        if (line.hasOption("with-constraint")) handleWithConstraintOption();
        else handleWithoutConstraintOption();
    }

    private void handleWithConstraintOption() {
        if (line.hasOption("default")) {
            System.err.println("L'option -d ne peut être utilisé avec l'option -c !");
            System.exit(0);
        } else if (line.hasOption("instance")) {
            (new GLIAAirlines()).solve(Instance.valueOf(line.getOptionValue("instance")), Main.timeout, Main.allSolutions);
        } else {
            for (Instance instance : Instance.values())
                (new GLIAAirlines()).solve(instance, Main.timeout, Main.allSolutions);
        }
    }

    private void handleWithoutConstraintOption() {
        if (line.hasOption("all")) {
            System.err.println("L'option -a doit être utilisé avec l'option -c !");
            System.exit(0);
        } else new base.utils.InstanceDisplay(line).display();
    }
}
