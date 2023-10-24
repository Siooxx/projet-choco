package base.utils;

import base.core.GLIAAirlinesNoConstraints;
import base.enums.Instance;
import org.apache.commons.cli.CommandLine;

// Affichage des instances
public class InstanceDisplay {

    private final CommandLine line;

    public InstanceDisplay(CommandLine line) {
        this.line = line;
    }

    public void display() {
        if (line.hasOption("instance")) displayInstanceResultWithoutConstraints();
        else displayAllInstancesWithoutConstraints();
    }

    private void displayInstanceResultWithoutConstraints() {
        final int nb_dividers = Instance.valueOf(line.getOptionValue("instance")).nb_dividers;
        final int capacity = Instance.valueOf(line.getOptionValue("instance")).capacity;
        final int[] exits = Instance.valueOf(line.getOptionValue("instance")).exits;

        System.out.println("Résultat de l'instance "
                + line.getOptionValue("instance").charAt(4)
                + " sans Programmation par Contraintes :");

        GLIAAirlinesNoConstraints.dividers(nb_dividers, capacity, exits);
    }

    private void displayAllInstancesWithoutConstraints() {
        System.out.println("Affichage de toutes les instances sans programmation par contrainte :\n");
        int i = 0;
        for (Instance instance : Instance.values()) {
            System.out.println("Instance " + ++i + " :");
            GLIAAirlinesNoConstraints.dividers(instance.nb_dividers, instance.capacity, instance.exits);
            System.out.println("\n");
        }
    }
}